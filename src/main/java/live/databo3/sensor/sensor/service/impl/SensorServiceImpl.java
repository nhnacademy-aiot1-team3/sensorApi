package live.databo3.sensor.sensor.service.impl;

import live.databo3.sensor.exception.already_exist_exception.SensorAlreadyExistException;
import live.databo3.sensor.exception.not_exist_exception.OrganizationNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorTypeNotExistException;
import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.organization.repository.OrganizationRepository;
import live.databo3.sensor.sensor.dto.SensorDto;
import live.databo3.sensor.sensor.dto.request.ModifySensorRequest;
import live.databo3.sensor.sensor.dto.request.RegisterSensorRequest;
import live.databo3.sensor.sensor.dto.SensorResponse;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor.repository.SensorRepository;
import live.databo3.sensor.sensor.service.SensorService;
import live.databo3.sensor.sensor_type.entity.SensorType;
import live.databo3.sensor.sensor_type.repository.SensorTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {
    private final SensorRepository sensorRepository;
    private final OrganizationRepository organizationRepository;

    public SensorResponse registerSensor(Integer organizationId, RegisterSensorRequest request) {
        String sensorSn = request.getSensorSn();
        if (sensorRepository.existsById(sensorSn)) {
            throw new SensorAlreadyExistException(sensorSn);
        }

        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new OrganizationNotExistException(organizationId));

        Sensor sensor = new Sensor(sensorSn, request.getSensorName(), request.getSensorPlace(), organization);

        sensorRepository.save(sensor);

        return sensor.toDto();
    }

    @Transactional
    public SensorResponse modifySensor(Integer organizationId, String sensorSn, ModifySensorRequest request) {
        Sensor sensor = sensorRepository.findBySensorSnAndOrganization_OrganizationId(sensorSn, organizationId).orElseThrow(() -> new SensorNotExistException(sensorSn));

        sensor.setSensorName(request.getSensorName());
        sensor.setSensorPlace(request.getSensorPlace());

        return sensor.toDto();
    }

    public List<SensorDto> getSensors(Integer organizationId) {
        return sensorRepository.findAllByOrganization_OrganizationId(organizationId);
    }

    public SensorDto getSensor(Integer organizationId, String sensorSn) {
        return sensorRepository.findOneBySensorSnAndOrganization_OrganizationId(sensorSn, organizationId).orElseThrow(() -> new SensorNotExistException(sensorSn));
    }

    @Transactional
    public void deleteSensor(Integer organizationId, String sensorSn) {
        if (!sensorRepository.existsBySensorSnAndOrganization_OrganizationId(sensorSn, organizationId)) {
            throw new SensorNotExistException(sensorSn);
        }
        sensorRepository.deleteById(sensorSn);
    }
}
