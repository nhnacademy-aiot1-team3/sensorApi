package live.databo3.sensor.sensor_type.service;

import live.databo3.sensor.sensor_type.dto.request.ModifySensorTypeRequest;
import live.databo3.sensor.sensor_type.dto.request.RegisterSensorTypeRequest;
import live.databo3.sensor.sensor_type.entity.SensorType;

import java.util.List;

public interface SensorTypeService {
    SensorType registerSensorType(RegisterSensorTypeRequest request);
    SensorType modifySensorType(ModifySensorTypeRequest request);
    List<SensorType> getSensorTypes();
    void deleteSensorTypes(Integer sensorTypeId);
}
