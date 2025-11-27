package cn.adrian.big.player.base.config;

import cn.adrian.big.player.base.utils.SpringContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通用模块的配置类
 *
 * @author adrian
 */
@Configuration
public class BaseConfiguration {

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }
}
