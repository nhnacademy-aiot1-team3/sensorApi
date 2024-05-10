package live.databo3.sensor.redis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import live.databo3.sensor.general_config.dto.GeneralConfigDto;
import live.databo3.sensor.general_config.dto.GeneralConfigForRedis;
import live.databo3.sensor.general_config.service.GeneralConfigService;
import live.databo3.sensor.organization.service.OrganizationService;
import live.databo3.sensor.value_config.dto.ValueConfigDto;
import live.databo3.sensor.value_config.dto.ValueConfigForRedisDto;
import live.databo3.sensor.value_config.service.ValueConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 특정 키값의 redis 갱신 요청이 들어왔을 때, redis 에 특정 키 값의 데이터를 넣어줍니다.
 * 키값은 organizationName 이고,
 * generalConfig 의 값 일부와 valueConfig 의 값 일부를 redis hash 구조에 담아 넣어줍니다.
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisReloader {
    private final RedisTemplate<String, Object> redisTemplate;
    private final GeneralConfigService generalConfigService;
    private final ValueConfigService valueConfigService;
    private final OrganizationService organizationService;
    private final ObjectMapper objectMapper;

    public void reloadRedisWithOrganizationName(String organizationName) throws JsonProcessingException {
        Integer organizationId = organizationService.findIdByName(organizationName);

        List<GeneralConfigDto> generalConfigDtoList = generalConfigService.findGeneralConfigByOrganizationId(organizationId);
        List<ValueConfigDto> valueConfigDtoList = valueConfigService.getValueConfigListByOrganizationId(organizationId);

        for (GeneralConfigDto dto : generalConfigDtoList) {
            redisTemplate.opsForHash().put(organizationName, "sn/" + dto.getSensorSn() + "/type/" + dto.getSensorType() + "/dev/" + dto.getSensorSn() + "/general" , objectMapper.writeValueAsString(new GeneralConfigForRedis(dto.getFunctionName())));
        }
        for (ValueConfigDto dto : valueConfigDtoList) {
            redisTemplate.opsForHash().put(organizationName,"sn/" + dto.getSensorSn() + "/type/" + dto.getSensorType() + "/value", objectMapper.writeValueAsString(new ValueConfigForRedisDto(dto.getFirstEntry(), dto.getSecondEntry())));
        }
        log.debug("redis reloaded, key: " + organizationName);
    }
}
