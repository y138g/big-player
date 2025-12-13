package cn.adrian.big.player.user.domain.service;

import cn.adrian.big.player.api.user.response.UserOperatorResponse;
import cn.adrian.big.player.lock.DistributeLock;
import cn.adrian.big.player.user.domain.entity.User;
import cn.adrian.big.player.user.infrastructure.exception.UserException;
import cn.adrian.big.player.user.infrastructure.mapper.UserMapper;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.alicp.jetcache.template.QuickConfig;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static cn.adrian.big.player.api.user.constant.UserOperateTypeEnum.REGISTER;
import static cn.adrian.big.player.user.infrastructure.exception.UserErrorCode.DUPLICATE_TELEPHONE_NUMBER;
import static cn.adrian.big.player.user.infrastructure.exception.UserErrorCode.USER_OPERATE_FAILED;


/**
 * 用户服务
 * @author Adrian
 */
@Service
@Slf4j
public class UserService extends ServiceImpl<UserMapper, User> implements InitializingBean {

    private static final String NICK_NAME_PREFIX = "玩家_";

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserOperateStreamService userOperateStreamService;

    @Autowired
    private CacheManager cacheManager;

    /**
     * 用户昵称布隆过滤器
     */
    private RBloomFilter<String> nickNameBloomFilter;

    /**
     * 邀请码布隆过滤器
     */
    private RBloomFilter<String> inviteCodeBloomFilter;

    /**
     * 缓存用户信息
     */
    private Cache<String, User> idUserCache;

    @PostConstruct
    public void init() {
        QuickConfig idQc = QuickConfig.newBuilder(":user:cache:id:")
                .cacheType(CacheType.BOTH)
                .expire(Duration.ofHours(2))
                .syncLocal(true)
                .build();
        idUserCache = cacheManager.getOrCreateCache(idQc);
    }

    // 分布式锁，锁住手机号，同一个手机号会共享一把锁，在redis中的key为 USER_REGISTER:13800000000
    @DistributeLock(keyExpression = "#telephone", scene = "USER_REGISTER")
    public UserOperatorResponse register(String telephone, String inviteCode) {

        // 生成用户昵称和邀请码
        String randomString;
        String defaultNickName;

        do {
            randomString = RandomUtil.randomString(6).toUpperCase();
            // 默认昵称格式为：玩家_邀请码
            defaultNickName = NICK_NAME_PREFIX + randomString;
        } while (inviteCodeExist(randomString) || nickNameExist(defaultNickName));

        // 处理邀请码
        String inviterId = handleInviteCode(inviteCode);

        String finalRandomString = randomString;
        String finalDefaultNickName = defaultNickName;
        UserOperatorResponse userOperatorResponse = new UserOperatorResponse();

        transactionTemplate.execute(status -> {
            try {
                // 注册用户
                User user = register(telephone, finalRandomString, finalDefaultNickName, inviterId);
                Assert.isFalse(user == null, () -> new UserException(USER_OPERATE_FAILED));

                // 将用户昵称、邀请码注册到布隆过滤器
                addNickName2RBloomFilter(user.getNickName());
                addInviteCode2RBloomFilter(user.getInviteCode());
                updateUserCache(user.getId().toString(), user);

                // 记录流水
                Long streamResult = userOperateStreamService.insertStream(user, REGISTER);
                Assert.isTrue(streamResult != null, () -> new UserException(USER_OPERATE_FAILED));

                // 构建返回值
                userOperatorResponse.setSuccess(true);

                return null;
            } catch (Exception e) {
                log.error("[user]user register error, telephone:{}, errorMessage:{}", telephone, e.getMessage());
                throw new UserException(USER_OPERATE_FAILED);
            }
        });

        return userOperatorResponse;
    }

    /**
     * 根据id查询用户信息
     * @param userId
     * @return
     */
    @Cached(
            name = ":user:cache:id:",   // 缓存前缀
            cacheType = CacheType.BOTH, // 缓存类型，both是指 本地缓存+远程缓存（这里是redis）
            key = "#userId",            // SpEL表达式，拼接key :user:cache:id:123
            cacheNullValue = true       // 是否缓存null值，默认为false
    )
    @CacheRefresh(refresh = 60, timeUnit = TimeUnit.MINUTES) // 缓存60分钟刷新一次
    public User findById(Long userId) {
        return userMapper.findById(userId);
    }

    /**
     * 根据手机号查询用户信息
     * @param telephone
     * @return
     */
    public User findByTelephone(String telephone) {
        return userMapper.findByTelephone(telephone);
    }

    private void updateUserCache(String userId, User user) {
        idUserCache.put(userId, user);
    }

    /**
     * 添加邀请码到布隆过滤器
     * @param inviteCode
     * @return
     */
    private boolean addInviteCode2RBloomFilter(String inviteCode) {
        if (inviteCode == null) return true;
        return inviteCodeBloomFilter != null && inviteCodeBloomFilter.add(inviteCode);
    }

    /**
     * 添加昵称到布隆过滤器
     * @param nickName
     * @return
     */
    private boolean addNickName2RBloomFilter(String nickName) {
        if (nickName == null) return true;
        return nickNameBloomFilter != null && nickNameBloomFilter.add(nickName);
    }

    /**
     * 注册
     * @param telephone
     * @param randomString
     * @param defaultNickName
     * @param inviterId
     * @return
     */
    private User register(String telephone, String randomString, String defaultNickName, String inviterId) {
        // 查询手机号是否被注册
        User user = userMapper.findByTelephone(telephone);
        Assert.isTrue(user == null, () -> new UserException(DUPLICATE_TELEPHONE_NUMBER));

        User newUser = new User();
        newUser.register(telephone, defaultNickName, null, randomString, inviterId);

        return save(newUser) ? newUser : null;
    }

    /**
     * 查询邀请人
     * @param inviteCode
     * @return
     */
    private String handleInviteCode(String inviteCode) {
        if (StringUtils.isBlank(inviteCode)) return null;
        User inviteUser = userMapper.findByInviteCode(inviteCode);
        if (inviteUser == null) return null;
        return inviteUser.getId().toString();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 初始化昵称布隆过滤器，在redis中获取
        nickNameBloomFilter = redissonClient.getBloomFilter("nickName");
        // 如果布隆过滤器存在，则尝试初始化
        if (nickNameBloomFilter != null && !nickNameBloomFilter.isExists()) {
            nickNameBloomFilter.tryInit(100000L, 0.01);
        }

        // 初始化邀请码布隆过滤器
        inviteCodeBloomFilter = redissonClient.getBloomFilter("inviteCode");
        if (inviteCodeBloomFilter != null && !inviteCodeBloomFilter.isExists()) {
            inviteCodeBloomFilter.tryInit(100000L, 0.01);
        }
    }

    /**
     * 判断昵称是否存在
     * @param nickName
     * @return 存在为true
     */
    public boolean nickNameExist(String nickName) {
        if (nickNameBloomFilter == null || !nickNameBloomFilter.contains(nickName)) return false;
        // 布隆过滤器原理，若存在需要查库做二次判断
        return userMapper.findByNickName(nickName) != null;
    }

    /**
     * 判断邀请码是否存在
     * @param inviteCode
     * @return
     */
    public boolean inviteCodeExist(String inviteCode) {
        if (inviteCodeBloomFilter == null || !inviteCodeBloomFilter.contains(inviteCode)) return false;
        // 布隆过滤器原理，若存在需要查库做二次判断
        return userMapper.findByInviteCode(inviteCode) != null;
    }
}
