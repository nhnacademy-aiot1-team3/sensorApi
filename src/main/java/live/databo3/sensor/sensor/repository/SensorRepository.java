package live.databo3.sensor.sensor.repository;

import live.databo3.sensor.sensor.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, String> {

}
