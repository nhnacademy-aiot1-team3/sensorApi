package live.databo3.sensor.value_config.service.impl;

import live.databo3.sensor.exception.already_exist_exception.ValueConfigAlreadyExistException;
import live.databo3.sensor.exception.not_exist_exception.GeneralConfigNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorTypeNotExistException;
import live.databo3.sensor.exception.not_exist_exception.ValueConfigNotExistException;
import live.databo3.sensor.general_config.entity.GeneralConfig;
import live.databo3.sensor.general_config.repository.GeneralConfigRepository;
import live.databo3.sensor.sensor_type.repository.SensorTypeRepository;
import live.databo3.sensor.value_config.dto.ValueConfigForRedisDto;
import live.databo3.sensor.value_config.dto.ValueConfigRequest;
import live.databo3.sensor.value_config.dto.ValueConfigResponse;
import live.databo3.sensor.value_config.entity.ValueConfig;
import live.databo3.sensor.value_config.repository.ValueConfigRepository;
import live.databo3.sensor.value_config.service.ValueConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ValueConfigServiceImpl implements ValueConfigService {
    private final SensorTypeRepository sensorTypeRepository;
    private final GeneralConfigRepository generalConfigRepository;
    private final ValueConfigRepository valueConfigRepository;

    private final List<String> manyToOneTypes = List.of("magnetic");

    public ValueConfigResponse createValueConfig(Integer organizationId, String sensorSn, Integer sensorTypeId, ValueConfigRequest request) {
        if (!manyToOneTypes.contains(sensorTypeRepository.findById(sensorTypeId).orElseThrow(() -> new SensorTypeNotExistException(sensorTypeId)).getSensorType())
                && valueConfigRepository.existsByGeneralConfig_SensorTypeMappings_Sensor_SensorSnAndGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdAndGeneralConfig_SensorTypeMappings_SensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId)) {
            throw new ValueConfigAlreadyExistException(sensorSn, sensorTypeId);
        }
        GeneralConfig generalConfig = generalConfigRepository.findBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId).orElseThrow(() -> new GeneralConfigNotExistException(sensorSn, sensorTypeId));
        ValueConfig valueConfig = new ValueConfig(null, request.getFirstEntry(), request.getSecondEntry(), generalConfig);

        return valueConfigRepository.save(valueConfig).toDto();
    }

    @Transactional
    public ValueConfigResponse modifyValueConfig(Integer organizationId, String sensorSn, Integer sensorTypeId, Long valueConfigNumber, ValueConfigRequest request) {
        ValueConfig valueConfig = valueConfigRepository.findByGeneralConfig_SensorTypeMappings_Sensor_SensorSnAndGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdAndGeneralConfig_SensorTypeMappings_SensorType_SensorTypeIdAndValueConfigNumber(sensorSn, organizationId, sensorTypeId, valueConfigNumber).orElseThrow(() -> new ValueConfigNotExistException(valueConfigNumber));

        valueConfig.setFirstEntry(request.getFirstEntry());
        valueConfig.setSecondEntry(request.getSecondEntry());

        return valueConfig.toDto();
    }

    @Transactional
    public void deleteValueConfig(Integer organizationId, String sensorSn, Integer sensorTypeId, Long valueConfigNumber) {
        if (!valueConfigRepository.existsByGeneralConfig_SensorTypeMappings_Sensor_SensorSnAndGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdAndGeneralConfig_SensorTypeMappings_SensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId) && valueConfigRepository.existsById(valueConfigNumber)) {
            throw new ValueConfigNotExistException(valueConfigNumber);
        }

        valueConfigRepository.deleteByGeneralConfig_SensorTypeMappings_Sensor_SensorSnAndGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdAndGeneralConfig_SensorTypeMappings_SensorType_SensorTypeIdAndValueConfigNumber(sensorSn, organizationId, sensorTypeId, valueConfigNumber);
    }

    public ValueConfigResponse getValueConfig(Integer organizationId, String sensorSn, Integer sensorTypeId, Long valueConfigNumber) {
        return valueConfigRepository.findByGeneralConfig_SensorTypeMappings_Sensor_SensorSnAndGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdAndGeneralConfig_SensorTypeMappings_SensorType_SensorTypeIdAndValueConfigNumber(sensorSn, organizationId, sensorTypeId, valueConfigNumber).orElseThrow(() -> new ValueConfigNotExistException(valueConfigNumber)).toDto();
    }

    public List<ValueConfigForRedisDto> getValueConfigListByOrganizationId(Integer organizationId) {
        List<ValueConfig> valueConfigList = valueConfigRepository.findAllByGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationId(organizationId);
        List<ValueConfigForRedisDto> valueConfigDtoList = new ArrayList<>();
        for (ValueConfig valueConfig : valueConfigList) {
            valueConfigDtoList.add(
                    new ValueConfigForRedisDto(valueConfig.getGeneralConfig().getSensorTypeMappings().getSensor().getSensorSn(),
                    valueConfig.getGeneralConfig().getSensorTypeMappings().getSensorType().getSensorType(),
                    valueConfig.getFirstEntry(),
                    valueConfig.getSecondEntry()));
        }
        return valueConfigDtoList;
    }
}
