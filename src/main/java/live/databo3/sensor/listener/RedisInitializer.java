package live.databo3.sensor.listener;

import live.databo3.sensor.annotations.ClearRedis;
import org.springframework.stereotype.Component;

/**
 * ClearRedis 를 통해 특정 조직의 설정 값을 지우는 initializer
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Component
public class RedisInitializer {
    @ClearRedis
    public void redisInitialize(Integer organizationId) {
    }
}
