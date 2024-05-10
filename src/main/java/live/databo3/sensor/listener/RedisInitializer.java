package live.databo3.sensor.listener;

import live.databo3.sensor.annotations.ClearRedis;
import org.springframework.stereotype.Component;

@Component
public class RedisInitializer {
    @ClearRedis
    public void redisInitialize(Integer organizationId) {
    }
}
