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

/**
 * sensorType entity 관련 service
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Service
@RequiredArgsConstructor
public class SensorTypeServiceImpl implements SensorTypeService {
    private final SensorTypeRepository sensorTypeRepository;

    /**
     * sensorType 을 등록한다.
     * @since 1.0.0
     */
    public SensorType registerSensorType(RegisterSensorTypeRequest request) {
        SensorType sensorType = new SensorType(null, request.getSensorType());
        sensorTypeRepository.save(sensorType);

        return sensorType;
    }

    /**
     * sensorType 을 수정한다..
     * @since 1.0.0
     */
    @Transactional
    public SensorType modifySensorType(Integer sensorTypeId, ModifySensorTypeRequest request) {
        SensorType sensorType = sensorTypeRepository.findById(sensorTypeId).orElseThrow(() -> new SensorTypeNotExistException(sensorTypeId));
        sensorType.setSensorType(request.getSensorType());
        return sensorType;
    }

    /**
     * 모든 sensorType 을 조회한다.
     * @since 1.0.0
     */
    public List<SensorType> getSensorTypes() {
        return sensorTypeRepository.findAll();
    }

    /**
     * sensorType 을 삭제한다.
     * @since 1.0.0
     */
    public void deleteSensorTypes(Integer sensorTypeId) {
        if (!sensorTypeRepository.existsById(sensorTypeId)) {
            throw new SensorTypeNotExistException(sensorTypeId);
        }
        sensorTypeRepository.deleteById(sensorTypeId);
    }
}
