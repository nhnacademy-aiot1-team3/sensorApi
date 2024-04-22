package live.databo3.sensor.general_config.repository;

import live.databo3.sensor.general_config.entity.TemperatureConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemperatureConfigRepository extends JpaRepository<TemperatureConfig, Long> {
    List<TemperatureConfig> findAllByGeneralConfig_Sensor_Organization_OrganizationName(String name);
}
