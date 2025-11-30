package cn.adrian.big.player.cache.config;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存配置
 * @author Adrian
 */
@Configuration
@EnableMethodCache(basePackages = "cn.adrian.big.player")
public class CacheConfiguration {
}
