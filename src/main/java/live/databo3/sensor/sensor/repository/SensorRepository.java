package live.databo3.sensor.sensor.repository;

import live.databo3.sensor.sensor.dto.SensorDto;
import live.databo3.sensor.sensor.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, String> {
    Optional<Sensor> findBySensorSnAndOrganization_OrganizationId(String sensorSn, Integer organizationId);
    Optional<SensorDto> findOneBySensorSnAndOrganization_OrganizationId(String sensorSn, Integer organizationId);
    List<SensorDto> findAllByOrganization_OrganizationId(Integer organizationId);
    boolean existsBySensorSnAndOrganization_OrganizationId(String sensorSn, Integer organizationId);


}
