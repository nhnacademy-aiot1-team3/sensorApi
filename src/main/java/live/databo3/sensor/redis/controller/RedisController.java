package live.databo3.sensor.redis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import live.databo3.sensor.redis.service.RedisReloader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 특정 키값의 redis 갱신 요청이 들어왔을 때, redis 에 특정 키 값의 데이터를 넣어줍니다.
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/org")
public class RedisController {

    private final RedisReloader redisReloader;

    @GetMapping("/{organizationName}/config")
    public void reloadRedis(@PathVariable String organizationName) throws JsonProcessingException {
        redisReloader.reloadRedisWithOrganizationName(organizationName);
    }
}
