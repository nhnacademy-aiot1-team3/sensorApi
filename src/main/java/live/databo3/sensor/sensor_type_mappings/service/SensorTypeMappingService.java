package live.databo3.sensor.sensor_type_mappings.service;

import live.databo3.sensor.sensor_type_mappings.dto.ModifySensorTypeMappingRequest;
import live.databo3.sensor.sensor_type_mappings.dto.SensorTypeMappingListDto;
import live.databo3.sensor.sensor_type_mappings.dto.SensorTypeMappingResponse;

import java.util.List;

public interface SensorTypeMappingService {
    SensorTypeMappingResponse registerSensorTypeMapping(String sensorSn, Integer organizationId, Integer sensorTypeId);
    SensorTypeMappingResponse modifySensorTypeMapping(String sensorSn, Integer organizationId, Integer sensorTypeId, ModifySensorTypeMappingRequest request);
    SensorTypeMappingResponse getSensorTypeMapping(String sensorSn, Integer organizationId, Integer sensorTypeId);
    void deleteSensorTypeMapping(String sensorSn, Integer organizationId, Integer sensorTypeId);

    List<SensorTypeMappingListDto> getSensorTypeMappingList(Integer organizationId);
}
