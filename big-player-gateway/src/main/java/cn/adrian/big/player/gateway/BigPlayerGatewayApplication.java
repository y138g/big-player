package cn.adrian.big.player.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author adrian
 */
@SpringBootApplication(scanBasePackages = "cn.adrian.big.player.gateway")
public class BigPlayerGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigPlayerGatewayApplication.class, args);
    }

}
