package auth;

import cn.adrian.big.player.user.BigPlayerUserApplication;
import cn.adrian.big.player.user.domain.service.UserOperateStreamService;
import cn.adrian.big.player.user.domain.service.UserService;
import com.alicp.jetcache.CacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BigPlayerUserApplication.class})
@ActiveProfiles("test")
public class UserBaseTest {

    @MockBean
    private RedissonClient redissonClient;

    @MockBean
    private UserOperateStreamService userOperateStreamService;

    @MockBean
    private UserService userService;

    @MockBean
    private CacheManager cacheManager;

    @Test
    public void test() {

    }
}
