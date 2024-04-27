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
    private final SensorTypeRepository sensorTypeRepository;
    private final OrganizationRepository organizationRepository;

    public SensorResponse registerSensor(RegisterSensorRequest request) {
        String sensorSn = request.getSensorSn();
        if (sensorRepository.existsById(sensorSn)) {
            throw new SensorAlreadyExistException(sensorSn);
        }

        Integer organizationId = request.getOrganizationId();
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new OrganizationNotExistException(organizationId));

        Integer sensorTypeId = request.getSensorTypeId();
        SensorType sensorType = sensorTypeRepository.findById(sensorTypeId).orElseThrow(() -> new SensorTypeNotExistException(sensorTypeId));

        Sensor sensor = new Sensor(sensorSn, request.getSensorName(), request.getSensorPlace(), organization, sensorType);

        sensorRepository.save(sensor);

        return sensor.toDto();
    }

    @Transactional
    public SensorResponse modifySensor(ModifySensorRequest request) {
        String sensorSn = request.getSensorSn();
        Sensor sensor = sensorRepository.findById(sensorSn).orElseThrow(() -> new SensorNotExistException(sensorSn));

        Integer organizationId = request.getOrganizationId();
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new OrganizationNotExistException(organizationId));

        Integer sensorTypeId = request.getSensorTypeId();
        SensorType sensorType = sensorTypeRepository.findById(sensorTypeId).orElseThrow(() -> new SensorTypeNotExistException(sensorTypeId));

        sensor.setOrganization(organization);
        sensor.setSensorType(sensorType);
        sensor.setSensorName(request.getSensorName());
        sensor.setSensorPlace(request.getSensorPlace());

        return sensor.toDto();
    }

    public List<SensorDto> getSensors(Integer organizationId) {
        return sensorRepository.findAllByOrganization_OrganizationId(organizationId);
    }

    public SensorDto getSensor(String sensorSn) {
        return sensorRepository.findSensorBySensorSn(sensorSn).orElseThrow(() -> new SensorNotExistException(sensorSn));
    }

    public void deleteSensor(String sensorSn) {
        if (!sensorRepository.existsById(sensorSn)) {
            throw new SensorNotExistException(sensorSn);
        }
        sensorRepository.deleteById(sensorSn);
    }
}
