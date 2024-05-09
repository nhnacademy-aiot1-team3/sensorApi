package live.databo3.sensor.redis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import live.databo3.sensor.redis.service.RedisReloader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/org")
public class RedisController {

    private final RedisReloader redisRefresher;

    @GetMapping("/{organizationName}/config")
    public void reloadRedis(@PathVariable String organizationName) throws JsonProcessingException {
        redisRefresher.reloadRedisWithOrganizationName(organizationName);
    }
}
