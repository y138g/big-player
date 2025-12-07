package cn.adrian.big.player.user;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Adrian
 */
@SpringBootApplication(scanBasePackages = "cn.adrian.big.player.user")
@EnableDubbo
public class BigPlayerUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigPlayerUserApplication.class, args);
    }

}
