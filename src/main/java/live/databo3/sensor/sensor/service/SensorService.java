package live.databo3.sensor.sensor.service;

import live.databo3.sensor.sensor.dto.SensorDto;
import live.databo3.sensor.sensor.dto.request.ModifySensorRequest;
import live.databo3.sensor.sensor.dto.request.RegisterSensorRequest;
import live.databo3.sensor.sensor.dto.SensorResponse;

import java.util.List;

public interface SensorService {
    SensorResponse registerSensor(RegisterSensorRequest request);
    SensorResponse modifySensor(ModifySensorRequest request);
    List<SensorDto> getSensors(Integer organizationId);
    SensorDto getSensor(String sensorSn);
    void deleteSensor(String sensorSn);
}
