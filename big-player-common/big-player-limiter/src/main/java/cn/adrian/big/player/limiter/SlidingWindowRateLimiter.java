package cn.adrian.big.player.limiter;

import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;

/**
 * 滑动窗口限流服务
 * @author Adrian
 */
public class SlidingWindowRateLimiter implements RateLimiter {

    private final RedissonClient redissonClient;

    private static final String LIMIT_KEY_PREFIX = "big:player:limit:";

    public SlidingWindowRateLimiter(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public Boolean tryAcquire(String key, int limit, int windowSize) {
        RRateLimiter rRateLimiter = redissonClient.getRateLimiter(LIMIT_KEY_PREFIX + key);

        if (!rRateLimiter.isExists()) {
            rRateLimiter.trySetRate(RateType.OVERALL, limit, windowSize, RateIntervalUnit.SECONDS);
        }

        return rRateLimiter.tryAcquire();
    }
}
