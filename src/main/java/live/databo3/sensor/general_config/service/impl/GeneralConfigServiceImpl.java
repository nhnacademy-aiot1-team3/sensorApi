package live.databo3.sensor.general_config.service.impl;

import live.databo3.sensor.annotations.RefreshRedis;
import live.databo3.sensor.exception.already_exist_exception.GeneralConfigAlreadyExistException;
import live.databo3.sensor.exception.not_exist_exception.GeneralConfigNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorTypeMappingNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SettingFunctionTypeNotExistException;
import live.databo3.sensor.general_config.dto.GeneralConfigDto;
import live.databo3.sensor.general_config.dto.request.ModifyGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.request.RegisterGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.response.GeneralConfigResponse;
import live.databo3.sensor.general_config.entity.GeneralConfig;
import live.databo3.sensor.general_config.repository.GeneralConfigRepository;
import live.databo3.sensor.general_config.service.GeneralConfigService;
import live.databo3.sensor.sensor_type_mappings.entity.SensorTypeMappings;
import live.databo3.sensor.sensor_type_mappings.repository.SensorTypeMappingRepository;
import live.databo3.sensor.setting_function_type.entity.SettingFunctionType;
import live.databo3.sensor.setting_function_type.repository.SettingFunctionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneralConfigServiceImpl implements GeneralConfigService {
    private final GeneralConfigRepository generalConfigRepository;
    private final SensorTypeMappingRepository sensorTypeMappingRepository;
    private final SettingFunctionTypeRepository settingFunctionTypeRepository;

    @RefreshRedis
    public GeneralConfigResponse registerGeneralConfig(Integer organizationId, String sensorSn, Integer sensorTypeId, RegisterGeneralConfigRequest request) {
        if (generalConfigRepository.existsBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_OrganizationIdAndSensorTypeMappings_SensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId)) {
            throw new GeneralConfigAlreadyExistException(sensorSn, sensorTypeId);
        }
        SensorTypeMappings sensorTypeMappings = sensorTypeMappingRepository.findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId).orElseThrow(() -> new SensorTypeMappingNotExistException(sensorSn, sensorTypeId));

        Long functionId = request.getFunctionId();
        SettingFunctionType settingFunctionType = settingFunctionTypeRepository.findById(functionId).orElseThrow(() -> new SettingFunctionTypeNotExistException(functionId));

        GeneralConfig generalConfig = new GeneralConfig(null, sensorTypeMappings, settingFunctionType, LocalDateTime.now());

        return (generalConfigRepository.save(generalConfig)).toDto();
    }

    @Transactional
    @RefreshRedis
    public GeneralConfigResponse modifyGeneralConfig(Integer organizationId, String sensorSn, Integer sensorTypeId, ModifyGeneralConfigRequest request) {
        GeneralConfig generalConfig = generalConfigRepository.findBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId).orElseThrow(() -> new GeneralConfigNotExistException(sensorSn, sensorTypeId));

        Long functionId = request.getFunctionId();
        SettingFunctionType settingFunctionType = settingFunctionTypeRepository.findById(functionId).orElseThrow(() -> new SettingFunctionTypeNotExistException(functionId));

        generalConfig.setSettingFunctionType(settingFunctionType);
        generalConfig.setLastUpdateDate(LocalDateTime.now());
        return generalConfig.toDto();
    }

    public List<GeneralConfigDto> findGeneralConfigByOrganizationId(Integer organizationId) {
        List<GeneralConfig> genralConfigList = generalConfigRepository.findAllBySensorTypeMappings_Sensor_Organization_OrganizationId(organizationId);
        List<GeneralConfigDto> generalConfigDtoList = new ArrayList<>();
        for (GeneralConfig generalConfig : genralConfigList) {
            generalConfigDtoList.add(
                    new GeneralConfigDto(generalConfig.getSensorTypeMappings().getSensor().getSensorSn(),
                            generalConfig.getSensorTypeMappings().getSensorType().getSensorType(),
                            generalConfig.getSettingFunctionType().getFunctionName()));
        }
        return generalConfigDtoList;
    }

    public GeneralConfigResponse getGeneralConfig(Integer organizationId, String sensorSn, Integer sensorTypeId) {
        return generalConfigRepository.findBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId).orElseThrow(() -> new GeneralConfigNotExistException(sensorSn, sensorTypeId)).toDto();
    }

    @RefreshRedis
    @Transactional
    public void deleteGeneralConfig(Integer organizationId, String sensorSn, Integer sensorTypeId) {
        if (!generalConfigRepository.existsBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_OrganizationIdAndSensorTypeMappings_SensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId)) {
            throw new GeneralConfigNotExistException(sensorSn, sensorTypeId);
        }
        generalConfigRepository.deleteBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId);
    }
}
