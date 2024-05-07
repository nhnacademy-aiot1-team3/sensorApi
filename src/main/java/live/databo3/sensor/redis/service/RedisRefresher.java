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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RedisRefresher {
    private final RedisTemplate<String, Object> redisTemplate;
    private final GeneralConfigService generalConfigService;
    private final ValueConfigService valueConfigService;
    private final OrganizationService organizationService;
    private final ObjectMapper objectMapper;

    public void refreshRedisWithOrganizationName(String organizationName) throws JsonProcessingException {
        Integer organizationId = organizationService.findIdByName(organizationName);

        List<GeneralConfigDto> generalConfigDtoList = generalConfigService.findGeneralConfigByOrganizationId(organizationId);
        List<ValueConfigDto> valueConfigDtoList = valueConfigService.getValueConfigListByOrganizationId(organizationId);

        for (GeneralConfigDto dto : generalConfigDtoList) {
            redisTemplate.opsForHash().put(organizationName, "sn/" + dto.getSensorSn() + "/type/" + dto.getSensorType() + "/general" , objectMapper.writeValueAsString(new GeneralConfigForRedis(dto.getFunctionName())));
        }
        for (ValueConfigDto dto : valueConfigDtoList) {
            redisTemplate.opsForHash().put(organizationName,"sn/" + dto.getSensorSn() + "/type/" + dto.getSensorType() + "/value", objectMapper.writeValueAsString(new ValueConfigForRedisDto(dto.getFirstEntry(), dto.getSecondEntry())));
        }
    }
}
