package auth;

import cn.adrian.big.player.auth.BigPlayerAuthApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BigPlayerAuthApplication.class})
@ActiveProfiles("test")
public class AuthBaseTest {

    @Test
    public void test(){

    }
}
