package live.databo3.sensor.sensor.service;

import live.databo3.sensor.sensor.dto.SensorDto;
import live.databo3.sensor.sensor.dto.request.ModifySensorRequest;
import live.databo3.sensor.sensor.dto.request.RegisterSensorRequest;
import live.databo3.sensor.sensor.dto.SensorResponse;

import java.util.List;

public interface SensorService {
    SensorResponse registerSensor(Integer organizationId, RegisterSensorRequest request);
    SensorResponse modifySensor(Integer organizationId, String sensorSn, ModifySensorRequest request);
    List<SensorDto> getSensors(Integer organizationId);
    SensorDto getSensor(Integer organizationId, String sensorSn);
    void deleteSensor(Integer organizationId, String sensorSn);
}
