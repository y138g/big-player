package auth;

import cn.adrian.big.player.auth.BigPlayerAuthApplication;
import cn.dev33.satoken.stp.StpUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BigPlayerAuthApplication.class})
public class SaTokenTest {

    @Test
    public void test() {
        System.out.println(StpUtil.isLogin());
        Assert.assertFalse(StpUtil.isLogin());

        StpUtil.login(11111);

        System.out.println(StpUtil.isLogin());
        Assert.assertTrue(StpUtil.isLogin());
    }
}
