package live.databo3.sensor.listener;

import live.databo3.sensor.annotations.RefreshRedis;
import org.springframework.stereotype.Component;

@Component
public class RedisInitializer {
    @RefreshRedis
    public void redisInitialize(Integer organizationId) {
    }
}
