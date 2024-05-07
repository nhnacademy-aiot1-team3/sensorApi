package live.databo3.sensor.general_config.repository;

import live.databo3.sensor.general_config.entity.GeneralConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GeneralConfigRepository extends JpaRepository<GeneralConfig, Long> {

    List<GeneralConfig> findAllBySensorTypeMappings_Sensor_Organization_OrganizationId(Integer organizationId);
    boolean existsBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_OrganizationIdAndSensorTypeMappings_SensorType_SensorTypeId(String sensorSn, Integer organizationId, Integer sensorTypeId);

    Optional<GeneralConfig> findBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeId(String sensorSn, Integer organizationId, Integer sensorTypeId);

    void deleteBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeId(String sensorSn, Integer organizationId, Integer sensorTypeId);
}
