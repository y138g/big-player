package cn.adrian.big.player;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Adrian
 */
@SpringBootApplication(scanBasePackages = "cn.adrian.big.player")
public class BigPlayerAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigPlayerAppApplication.class, args);
    }

}
