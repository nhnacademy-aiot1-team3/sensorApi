package live.databo3.sensor.general_config.repository;

import live.databo3.sensor.general_config.entity.HumidityConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HumidityConfigRepository extends JpaRepository<HumidityConfig, Long> {
    List<HumidityConfig> findAllByGeneralConfig_Sensor_Organization_OrganizationName(String name);
}
