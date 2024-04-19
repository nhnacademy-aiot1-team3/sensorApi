package live.databo3.sensor.sensor.service.impl;

import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.organization.repository.OrganizationRepository;
import live.databo3.sensor.sensor.dto.RegisterSensorRequest;
import live.databo3.sensor.sensor.dto.RegisterSensorResponse;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor.repository.SensorRepository;
import live.databo3.sensor.sensor.service.SensorService;
import live.databo3.sensor.sensorType.entity.SensorType;
import live.databo3.sensor.sensorType.repository.SensorTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {
    private final SensorRepository sensorRepository;
    private final SensorTypeRepository sensorTypeRepository;
    private final OrganizationRepository organizationRepository;

    // todo exception
    public RegisterSensorResponse registerSensor(RegisterSensorRequest request) {
        Organization organization = organizationRepository.findById(request.getOrganizationId()).orElseThrow(() -> new RuntimeException("no organization"));
        SensorType sensorType = sensorTypeRepository.findById(request.getSensorTypeId()).orElseThrow(() -> new RuntimeException("no sensorType"));
        Sensor sensor = new Sensor(request.getSensorSn(), request.getSensorName(), request.getSensorPlace(),
                organization, sensorType);

        if (sensorRepository.existsById(request.getSensorSn())) {
            throw new RuntimeException("already exist sensor");
        }

        sensorRepository.save(sensor);

        return sensor.toDto();
    }
}
