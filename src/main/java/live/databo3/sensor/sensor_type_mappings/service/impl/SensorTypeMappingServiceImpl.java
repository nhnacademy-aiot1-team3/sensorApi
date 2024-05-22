package live.databo3.sensor.sensor_type_mappings.service.impl;

import live.databo3.sensor.annotations.ClearRedis;
import live.databo3.sensor.exception.already_exist_exception.SensorTypeMappingAlreadyExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorTypeMappingNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorTypeNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SettingFunctionTypeNotExistException;
import live.databo3.sensor.general_config.entity.GeneralConfig;
import live.databo3.sensor.general_config.repository.GeneralConfigRepository;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor.repository.SensorRepository;
import live.databo3.sensor.sensor_type.entity.SensorType;
import live.databo3.sensor.sensor_type.repository.SensorTypeRepository;
import live.databo3.sensor.sensor_type_mappings.dto.*;
import live.databo3.sensor.sensor_type_mappings.entity.SensorTypeMappings;
import live.databo3.sensor.sensor_type_mappings.repository.SensorTypeMappingRepository;
import live.databo3.sensor.sensor_type_mappings.service.SensorTypeMappingService;
import live.databo3.sensor.setting_function_type.entity.SettingFunctionType;
import live.databo3.sensor.setting_function_type.repository.SettingFunctionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * sensorTypeMapping entity 관련 service
 * CRUD 와 더불어 알맞은 조직의 sensorTypeMapping 을 요청했는지 쿼리를 통해 무결성을 검증하는 역할을 포함한다.
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Service
@RequiredArgsConstructor
public class SensorTypeMappingServiceImpl implements SensorTypeMappingService {
    private final SensorTypeMappingRepository sensorTypeMappingRepository;
    private final SensorRepository sensorRepository;
    private final SensorTypeRepository sensorTypeRepository;
    private final GeneralConfigRepository generalConfigRepository;
    private final SettingFunctionTypeRepository settingFunctionTypeRepository;

    /**
     * sensorTypeMapping 을 등록한다.
     * 이미 매핑이 존재하는지 확인 한 후 없다면 등록한다.
     * @since 1.0.0
     */
    @Transactional
    public SensorTypeMappingResponse registerSensorTypeMapping(String sensorSn, Integer organizationId, Integer sensorTypeId) {
        if (sensorTypeMappingRepository.existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId)) {
            throw new SensorTypeMappingAlreadyExistException(sensorSn, sensorTypeId);
        }
        Sensor sensor = sensorRepository.findBySensorSnAndOrganization_OrganizationId(sensorSn, organizationId).orElseThrow(() -> new SensorNotExistException(sensorSn));
        SensorType sensorType = sensorTypeRepository.findById(sensorTypeId).orElseThrow(() -> new SensorTypeNotExistException(sensorTypeId));

        SensorTypeMappings newSensorTypeMappings = new SensorTypeMappings(null, sensor, sensorType);
        SensorTypeMappings sensorTypeMappings = sensorTypeMappingRepository.save(newSensorTypeMappings);

        SettingFunctionType settingFunctionType = settingFunctionTypeRepository.findById(1L).orElseThrow(() -> new SettingFunctionTypeNotExistException(1L));
        generalConfigRepository.save(new GeneralConfig(null, sensorTypeMappings, settingFunctionType, null, LocalDateTime.now()));
        return sensorTypeMappings.toDto();
    }

    /**
     * sensorTypeMapping 을 수정한다.
     * 조건에 맞는 sensorType 을 조회하여 수정한다.
     * @since 1.0.0
     */
    @Transactional
    @ClearRedis
    public SensorTypeMappingResponse modifySensorTypeMapping(String sensorSn, Integer organizationId, Integer sensorTypeId, ModifySensorTypeMappingRequest request) {
        if (sensorTypeMappingRepository.existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(sensorSn, organizationId, request.getSensorTypeId())) {
            throw new SensorTypeMappingAlreadyExistException(sensorSn, sensorTypeId);
        }
        SensorTypeMappings sensorTypeMappings = sensorTypeMappingRepository.findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId).orElseThrow(() -> new SensorTypeMappingNotExistException(sensorSn, sensorTypeId));
        SensorType sensorType = sensorTypeRepository.findById(request.getSensorTypeId()).orElseThrow(() -> new SensorTypeNotExistException(sensorTypeId));
        sensorTypeMappings.setSensorType(sensorType);
        return sensorTypeMappingRepository.save(sensorTypeMappings).toDto();
    }

    /**
     * sensorTypeMapping 을 조회한다.
     * 조건에 맞는 sensorTypeMapping 을 조회하여 dto 를 반환한다.
     * @since 1.0.0
     */
    public SensorTypeMappingResponse getSensorTypeMapping(String sensorSn, Integer organizationId, Integer sensorTypeId) {
        SensorTypeMappings sensorTypeMappings = sensorTypeMappingRepository.findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId).orElseThrow(() -> new SensorTypeMappingNotExistException(sensorSn, sensorTypeId));
        return sensorTypeMappings.toDto();
    }

    /**
     * sensorTypeMapping 을 삭제한다.
     * 조건에 맞는 sensorTypeMapping 을 조회하여 삭제한다.
     * @since 1.0.0
     */
    @Transactional
    @ClearRedis
    public void deleteSensorTypeMapping(String sensorSn, Integer organizationId, Integer sensorTypeId) {
        SensorTypeMappings sensorTypeMappings = sensorTypeMappingRepository.findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId).orElseThrow(() -> new SensorTypeMappingNotExistException(sensorSn, sensorTypeId));
        sensorTypeMappingRepository.deleteById(sensorTypeMappings.getRecordNumber());
    }

    public List<SensorTypeMappingListDto> getSensorTypeMappingList(Integer organizationId) {
        return sensorTypeMappingRepository.findAllBySensor_Organization_OrganizationId(organizationId);
    }
}
