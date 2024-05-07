package live.databo3.sensor.redis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import live.databo3.sensor.redis.service.RedisRefresher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/sensor/redis")
public class RedisController {

    private final RedisRefresher redisRefresher;

    @GetMapping("/{organizationName}")
    public void refreshRedis(@PathVariable String organizationName) throws JsonProcessingException {
        redisRefresher.refreshRedisWithOrganizationName(organizationName);
    }
}
