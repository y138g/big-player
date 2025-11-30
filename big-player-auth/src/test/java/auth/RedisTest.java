package auth;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;

public class RedisTest extends AuthBaseTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testRedisConnect() {

        // 设置 key-value = auth-auth
        redisTemplate.opsForValue().set("auth", "auth");

        // 断言判断
        Assert.assertEquals("auth", redisTemplate.opsForValue().get("auth"));

        if (Objects.requireNonNull(redisTemplate.opsForValue().get("auth")).equals("auth")) {
            System.out.println("redis connect success");
            redisTemplate.delete("auth");
        }
    }
}
