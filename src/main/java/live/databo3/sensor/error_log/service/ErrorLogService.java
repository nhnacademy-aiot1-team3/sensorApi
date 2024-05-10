package live.databo3.sensor.error_log.service;

import live.databo3.sensor.error_log.dto.ErrorLogCreateRequestDto;
import live.databo3.sensor.error_log.dto.ErrorLogResponseDto;

import java.util.List;

public interface ErrorLogService {
    ErrorLogResponseDto createErrorLog(ErrorLogCreateRequestDto errorLogCreateRequestDto);
    void deleteErrorLog(Long logId);
    List<ErrorLogResponseDto> getErrorLogs(Integer organizationId);
}
