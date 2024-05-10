package live.databo3.sensor.error_log.repository;

import live.databo3.sensor.error_log.dto.ErrorLogResponseDto;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ErrorLogRepositoryCustom {
    Optional<List<ErrorLogResponseDto>> getErrorLogs(Integer organizationId);
}
