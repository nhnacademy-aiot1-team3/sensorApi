package live.databo3.sensor.sensor_type.repository;

import live.databo3.sensor.sensor_type.entity.SensorType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SensorTypeRepository extends JpaRepository<SensorType, Integer> {
}
