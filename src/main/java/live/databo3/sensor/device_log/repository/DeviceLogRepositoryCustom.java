package live.databo3.sensor.device_log.repository;

import live.databo3.sensor.device_log.dto.DeviceLogResponseDto;
import live.databo3.sensor.error_log.dto.ErrorLogResponseDto;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface DeviceLogRepositoryCustom {
    Optional<List<DeviceLogResponseDto>> getDeviceLogs(Integer organizationId);
}
