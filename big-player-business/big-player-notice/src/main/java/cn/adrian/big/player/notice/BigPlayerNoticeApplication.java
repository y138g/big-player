package cn.adrian.big.player.notice;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Adrian
 */
@SpringBootApplication(scanBasePackages = "cn.adrian.big.player.notice")
@EnableDubbo
public class BigPlayerNoticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigPlayerNoticeApplication.class, args);
    }

}
