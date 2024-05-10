package live.databo3.sensor.sensor_type.service.impl;

import live.databo3.sensor.exception.not_exist_exception.SensorTypeNotExistException;
import live.databo3.sensor.sensor_type.dto.request.ModifySensorTypeRequest;
import live.databo3.sensor.sensor_type.dto.request.RegisterSensorTypeRequest;
import live.databo3.sensor.sensor_type.entity.SensorType;
import live.databo3.sensor.sensor_type.repository.SensorTypeRepository;
import live.databo3.sensor.sensor_type.service.SensorTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorTypeServiceImpl implements SensorTypeService {
    private final SensorTypeRepository sensorTypeRepository;

    public SensorType registerSensorType(RegisterSensorTypeRequest request) {
        SensorType sensorType = new SensorType(null, request.getSensorType());
        sensorTypeRepository.save(sensorType);

        return sensorType;
    }

    @Transactional
    public SensorType modifySensorType(Integer sensorTypeId, ModifySensorTypeRequest request) {
        SensorType sensorType = sensorTypeRepository.findById(sensorTypeId).orElseThrow(() -> new SensorTypeNotExistException(sensorTypeId));
        sensorType.setSensorType(request.getSensorType());
        return sensorType;
    }

    public List<SensorType> getSensorTypes() {
        return sensorTypeRepository.findAll();
    }

    public void deleteSensorTypes(Integer sensorTypeId) {
        if (!sensorTypeRepository.existsById(sensorTypeId)) {
            throw new SensorTypeNotExistException(sensorTypeId);
        }
        sensorTypeRepository.deleteById(sensorTypeId);
    }
}
