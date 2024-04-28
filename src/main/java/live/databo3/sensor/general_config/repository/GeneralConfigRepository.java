package live.databo3.sensor.general_config.repository;

import live.databo3.sensor.general_config.entity.GeneralConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GeneralConfigRepository extends JpaRepository<GeneralConfig, Long> {
    @Query("SELECT g.sensor.organization.organizationName FROM GeneralConfig g WHERE g.configId = :configId")
    String findOrganizationNameByConfigId(@Param("configId") Long configId);

    List<GeneralConfig> findAllBySensor_Organization_OrganizationName(String name);

    @Query("select case when count(g)>0 then true else false end from GeneralConfig g where lower(g.sensor.sensorSn) like lower(:sensorSn)")
    boolean existsBySensorSn(String sensorSn);
}
