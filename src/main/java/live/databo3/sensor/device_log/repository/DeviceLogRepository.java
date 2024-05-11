package live.databo3.sensor.device_log.repository;

import live.databo3.sensor.device_log.entity.DeviceLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceLogRepository extends JpaRepository<DeviceLog, Long> {
}
