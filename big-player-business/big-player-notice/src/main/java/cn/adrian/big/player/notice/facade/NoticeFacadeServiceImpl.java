package cn.adrian.big.player.notice.facade;

import cn.adrian.big.player.api.notice.response.NoticeResponse;
import cn.adrian.big.player.api.notice.service.NoticeFacadeService;
import cn.adrian.big.player.base.exception.SystemException;
import cn.adrian.big.player.limiter.SlidingWindowRateLimiter;
import cn.adrian.big.player.notice.domain.entity.Notice;
import cn.adrian.big.player.notice.domain.service.NoticeService;
import cn.adrian.big.player.rpc.facade.Facade;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

import static cn.adrian.big.player.api.notice.constat.NoticeConstant.CAPTCHA_KEY_PREFIX;
import static cn.adrian.big.player.base.exception.BizErrorCode.SEND_NOTICE_DUPLICATED;
import static cn.adrian.big.player.rpc.constant.DubboConstant.DUBBO_SERVICE_VERSION_ONE;


/**
 * @author Hollis
 */
@Slf4j
@DubboService(version = DUBBO_SERVICE_VERSION_ONE)
public class NoticeFacadeServiceImpl implements NoticeFacadeService {

    @Autowired
    private SlidingWindowRateLimiter slidingWindowRateLimiter;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private NoticeService noticeService;

    /**
     * 生成并发送短信验证码
     * @param telephone
     * @return
     */
    @Facade
    @Override
    public NoticeResponse generateAndSendSmsCaptcha(String telephone) {

        Boolean access = slidingWindowRateLimiter.tryAcquire(telephone, 1, 60);

        if (!access) {
            throw new SystemException(SEND_NOTICE_DUPLICATED);
        }

        // 生成验证码
        String captcha = RandomUtil.randomNumbers(4);

        // 验证码存入Redis
        redisTemplate.opsForValue().set(CAPTCHA_KEY_PREFIX + telephone, captcha, 5, TimeUnit.MINUTES);

        // 发送短信，并记录数据库，因为我们还没有短信服务，所以使用日志替代
        log.info("[通知服务]发送短信验证码：{}，手机号：{}", captcha, telephone);
        Notice notice = noticeService.saveCaptcha(telephone, captcha);

        return new NoticeResponse.Builder().setSuccess(notice != null).build();
    }
}
