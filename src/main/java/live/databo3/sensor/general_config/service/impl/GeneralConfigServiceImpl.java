package live.databo3.sensor.general_config.service.impl;

import live.databo3.sensor.general_config.repository.GeneralConfigRepository;
import live.databo3.sensor.general_config.service.GeneralConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeneralConfigServiceImpl implements GeneralConfigService {
    private GeneralConfigRepository generalConfigRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public void registerConfig() {
    }
}
