package live.databo3.sensor.sensor_type.repository;

import live.databo3.sensor.sensor_type.entity.SensorType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorTypeRepository extends JpaRepository<SensorType, Integer> {
    Optional<SensorType> findBySensorType(String sensorType);
}
