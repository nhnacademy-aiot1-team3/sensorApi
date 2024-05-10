package live.databo3.sensor.sensor_type_mappings.service.impl;

import live.databo3.sensor.annotations.ClearRedis;
import live.databo3.sensor.exception.already_exist_exception.SensorTypeMappingAlreadyExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorTypeMappingNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorTypeNotExistException;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor.repository.SensorRepository;
import live.databo3.sensor.sensor_type.entity.SensorType;
import live.databo3.sensor.sensor_type.repository.SensorTypeRepository;
import live.databo3.sensor.sensor_type_mappings.dto.ModifySensorTypeMappingRequest;
import live.databo3.sensor.sensor_type_mappings.dto.SensorTypeMappingResponse;
import live.databo3.sensor.sensor_type_mappings.entity.SensorTypeMappings;
import live.databo3.sensor.sensor_type_mappings.repository.SensorTypeMappingRepository;
import live.databo3.sensor.sensor_type_mappings.service.SensorTypeMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SensorTypeMappingServiceImpl implements SensorTypeMappingService {
    private final SensorTypeMappingRepository sensorTypeMappingRepository;
    private final SensorRepository sensorRepository;
    private final SensorTypeRepository sensorTypeRepository;

    public SensorTypeMappingResponse registerSensorTypeMapping(String sensorSn, Integer organizationId, Integer sensorTypeId) {
        if (sensorTypeMappingRepository.existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId)) {
            throw new SensorTypeMappingAlreadyExistException(sensorSn, sensorTypeId);
        }
        Sensor sensor = sensorRepository.findBySensorSnAndOrganization_OrganizationId(sensorSn, organizationId).orElseThrow(() -> new SensorNotExistException(sensorSn));
        SensorType sensorType = sensorTypeRepository.findById(sensorTypeId).orElseThrow(() -> new SensorTypeNotExistException(sensorTypeId));

        SensorTypeMappings sensorTypeMappings = new SensorTypeMappings(null, sensor, sensorType);
        return sensorTypeMappingRepository.save(sensorTypeMappings).toDto();
    }

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

    public SensorTypeMappingResponse getSensorTypeMapping(String sensorSn, Integer organizationId, Integer sensorTypeId) {
        SensorTypeMappings sensorTypeMappings = sensorTypeMappingRepository.findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId).orElseThrow(() -> new SensorTypeMappingNotExistException(sensorSn, sensorTypeId));
        return sensorTypeMappings.toDto();
    }

    @Transactional
    @ClearRedis
    public void deleteSensorTypeMapping(String sensorSn, Integer organizationId, Integer sensorTypeId) {
        SensorTypeMappings sensorTypeMappings = sensorTypeMappingRepository.findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId).orElseThrow(() -> new SensorTypeMappingNotExistException(sensorSn, sensorTypeId));
        sensorTypeMappingRepository.deleteById(sensorTypeMappings.getRecordNumber());
    }
}
