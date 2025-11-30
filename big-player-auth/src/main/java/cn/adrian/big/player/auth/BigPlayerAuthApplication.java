package cn.adrian.big.player.auth;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Adrian
 */
@SpringBootApplication(scanBasePackages = {"cn.adrian.big.player.auth"})
@EnableDubbo
public class BigPlayerAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigPlayerAuthApplication.class, args);
    }

}
