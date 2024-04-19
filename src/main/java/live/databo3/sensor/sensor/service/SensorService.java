package live.databo3.sensor.sensor.service;

import live.databo3.sensor.sensor.dto.RegisterSensorRequest;
import live.databo3.sensor.sensor.dto.RegisterSensorResponse;

public interface SensorService {
    RegisterSensorResponse registerSensor(RegisterSensorRequest request);
}
