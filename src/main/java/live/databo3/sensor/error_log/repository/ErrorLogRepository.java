package live.databo3.sensor.error_log.repository;

import live.databo3.sensor.error_log.entity.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long>,ErrorLogRepositoryCustom {
}
