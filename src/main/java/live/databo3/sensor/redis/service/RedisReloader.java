package live.databo3.sensor.redis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import live.databo3.sensor.general_config.dto.GeneralConfigDto;
import live.databo3.sensor.general_config.dto.GeneralConfigForRedis;
import live.databo3.sensor.general_config.service.GeneralConfigService;
import live.databo3.sensor.organization.service.OrganizationService;
import live.databo3.sensor.sensor_type.entity.SensorType;
import live.databo3.sensor.sensor_type.service.SensorTypeService;
import live.databo3.sensor.sensor_type_mappings.dto.SensorListForRedisDto;
import live.databo3.sensor.sensor_type_mappings.service.impl.SensorListService;
import live.databo3.sensor.value_config.dto.ValueConfigDto;
import live.databo3.sensor.value_config.dto.ValueConfigForRedisDto;
import live.databo3.sensor.value_config.service.ValueConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    private final SensorListService sensorListService;
    private final SensorTypeService sensorTypeService;
    private final ObjectMapper objectMapper;


    /**
     * 특정 키값의 redis 갱신 요청이 들어왔을 때, redis 에 특정 키 값의 데이터를 넣어줍니다.
     * 센서리스트, 일반 설정, 값 설정 을 등록합니다.
     * @since 1.0.0
     */
    public void reloadRedisWithOrganizationName(String organizationName) throws JsonProcessingException {
        Integer organizationId = organizationService.findIdByName(organizationName);

        List<GeneralConfigDto> generalConfigDtoList = generalConfigService.findGeneralConfigByOrganizationId(organizationId);
        List<ValueConfigDto> valueConfigDtoList = valueConfigService.getValueConfigListByOrganizationId(organizationId);
        List<SensorListForRedisDto> sensorList = sensorListService.getSensorListByOrganizationName(organizationName);

        for (SensorListForRedisDto dto : sensorList) {
            redisTemplate.opsForHash().put(organizationName, "sensorList:" + dto.getSensorSn(), objectMapper.writeValueAsString(dto.getSensorTypes()));
        }

        for (GeneralConfigDto dto : generalConfigDtoList) {
            redisTemplate.opsForHash().put(organizationName, "general:" + dto.getSensorSn() + "/" + dto.getSensorType() , objectMapper.writeValueAsString(new GeneralConfigForRedis(dto.getDeviceSn(), dto.getFunctionName())));
        }
        for (ValueConfigDto dto : valueConfigDtoList) {
            redisTemplate.opsForHash().put(organizationName, "value:" + dto.getSensorSn() + "/" + dto.getSensorType() , objectMapper.writeValueAsString(new ValueConfigForRedisDto(dto.getFirstEntry(), dto.getSecondEntry())));
        }
    }


    /**
     * 센서 타입의 목록을 레디스에 로드합니다.
     * @since 1.0.0
     */
    public void reloadSensorTypes() {
        List<SensorType> sensorTypelist = sensorTypeService.getSensorTypes();
        for (SensorType sensorType : sensorTypelist) {
            redisTemplate.opsForList().rightPush("sensorList", sensorType.getSensorType());
        }
    }
}
