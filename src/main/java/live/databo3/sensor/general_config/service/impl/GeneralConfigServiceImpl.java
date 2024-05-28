package live.databo3.sensor.general_config.service.impl;

import live.databo3.sensor.annotations.ClearRedis;
import live.databo3.sensor.device.entity.Device;
import live.databo3.sensor.device.repository.DeviceRepository;
import live.databo3.sensor.exception.already_exist_exception.GeneralConfigAlreadyExistException;
import live.databo3.sensor.exception.not_exist_exception.DeviceNotExistException;
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
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * generalConfig entity 관련 service
 * CRUD 와 더불어 알맞은 조직의 generalConfig 를 요청했는지 쿼리를 통해 무결성을 검증하는 역할을 포함한다.
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Service
@RequiredArgsConstructor
public class GeneralConfigServiceImpl implements GeneralConfigService {
    private final GeneralConfigRepository generalConfigRepository;
    private final SensorTypeMappingRepository sensorTypeMappingRepository;
    private final SettingFunctionTypeRepository settingFunctionTypeRepository;
    private final DeviceRepository deviceRepository;

    /**
     * 설정을 등록하고자 하는 센서가 존재하는지 확인 후 존재한다면 request 의 body 를 통해 생성한다.
     * @since 1.0.0
     */
    @ClearRedis
    public GeneralConfigResponse registerGeneralConfig(Integer organizationId, String sensorSn, Integer sensorTypeId, RegisterGeneralConfigRequest request) {
        if (generalConfigRepository.existsBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_OrganizationIdAndSensorTypeMappings_SensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId)) {
            throw new GeneralConfigAlreadyExistException(sensorSn, sensorTypeId);
        }
        SensorTypeMappings sensorTypeMappings = sensorTypeMappingRepository.findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId).orElseThrow(() -> new SensorTypeMappingNotExistException(sensorSn, sensorTypeId));

        Long functionId = request.getFunctionId();
        SettingFunctionType settingFunctionType = settingFunctionTypeRepository.findById(functionId).orElseThrow(() -> new SettingFunctionTypeNotExistException(functionId));

        GeneralConfig generalConfig;
        if (StringUtils.hasText(request.getDeviceSn())) {
            String deviceSn = request.getDeviceSn();
            Device device = deviceRepository.findByDeviceSnAndOrganization_OrganizationId(deviceSn, organizationId).orElseThrow(() -> new DeviceNotExistException(deviceSn));
            generalConfig = new GeneralConfig(null, sensorTypeMappings, settingFunctionType, device, LocalDateTime.now());
        } else {
            generalConfig = new GeneralConfig(null, sensorTypeMappings, settingFunctionType, null, LocalDateTime.now());
        }

        return (generalConfigRepository.save(generalConfig)).toDto();
    }

    /**
     * 설정이 존재하는지 확인 후 존재한다면 request 의 body 를 통해 수정한다.
     * @since 1.0.0
     */
    @Transactional
    @ClearRedis
    public GeneralConfigResponse modifyGeneralConfig(Integer organizationId, String sensorSn, Integer sensorTypeId, ModifyGeneralConfigRequest request) {
        GeneralConfig generalConfig = generalConfigRepository.findBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId).orElseThrow(() -> new GeneralConfigNotExistException(sensorSn, sensorTypeId));

        Long functionId = request.getFunctionId();
        SettingFunctionType settingFunctionType = settingFunctionTypeRepository.findById(functionId).orElseThrow(() -> new SettingFunctionTypeNotExistException(functionId));

        if (StringUtils.hasText(request.getDeviceSn())) {
            String deviceSn = request.getDeviceSn();
            Device device = deviceRepository.findByDeviceSnAndOrganization_OrganizationId(deviceSn, organizationId).orElseThrow(() -> new DeviceNotExistException(deviceSn));
            generalConfig.setDevice(device);
        } else {
            generalConfig.setDevice(null);
        }

        generalConfig.setSettingFunctionType(settingFunctionType);
        generalConfig.setLastUpdateDate(LocalDateTime.now());
        return generalConfig.toDto();
    }

    /**
     * 특정 조직에 해당하는 설정을 모두 조회한다.
     * @since 1.0.0
     */
    public List<GeneralConfigDto> findGeneralConfigByOrganizationId(Integer organizationId) {
        List<GeneralConfig> genralConfigList = generalConfigRepository.findAllBySensorTypeMappings_Sensor_Organization_OrganizationId(organizationId);
        List<GeneralConfigDto> generalConfigDtoList = new ArrayList<>();
        for (GeneralConfig generalConfig : genralConfigList) {
            generalConfigDtoList.add(
                    new GeneralConfigDto(generalConfig.getSensorTypeMappings().getSensor().getSensorSn(),
                            generalConfig.getSensorTypeMappings().getSensorType().getSensorType(),
                            generalConfig.getSettingFunctionType().getFunctionName().name(),
                            Objects.isNull(generalConfig.getDevice()) ? null : generalConfig.getDevice().getDeviceSn()
                            ));
        }
        return generalConfigDtoList;
    }

    /**
     * 특정 설정을 조회한다.
     * @since 1.0.0
     */
    public GeneralConfigResponse getGeneralConfig(Integer organizationId, String sensorSn, Integer sensorTypeId) {
        return generalConfigRepository.findBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId).orElseThrow(() -> new GeneralConfigNotExistException(sensorSn, sensorTypeId)).toDto();
    }

    /**
     * 특정 설정이 존재하는지 확인 후 존재한다면 삭제한다.
     * @since 1.0.0
     */
    @ClearRedis
    @Transactional
    public void deleteGeneralConfig(Integer organizationId, String sensorSn, Integer sensorTypeId) {
        if (!generalConfigRepository.existsBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_OrganizationIdAndSensorTypeMappings_SensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId)) {
            throw new GeneralConfigNotExistException(sensorSn, sensorTypeId);
        }
        generalConfigRepository.deleteBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId);
    }
}
