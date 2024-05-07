package live.databo3.sensor.sensor_type_mappings.repository;

import live.databo3.sensor.sensor_type_mappings.entity.SensorTypeMappings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorTypeMappingRepository extends JpaRepository<SensorTypeMappings, Long> {
    Optional<SensorTypeMappings> findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(String sensorSn, Integer organizationId, Integer sensorTypeId);
    boolean existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(String sensorSn, Integer organizationId, Integer sensorTypeId);


}
