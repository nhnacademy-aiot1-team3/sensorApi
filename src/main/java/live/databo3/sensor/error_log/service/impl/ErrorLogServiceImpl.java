package live.databo3.sensor.error_log.service.impl;

import live.databo3.sensor.error_log.dto.ErrorLogCreateRequestDto;
import live.databo3.sensor.error_log.dto.ErrorLogResponseDto;
import live.databo3.sensor.error_log.entity.ErrorLog;
import live.databo3.sensor.error_log.repository.ErrorLogRepository;
import live.databo3.sensor.error_log.service.ErrorLogService;
import live.databo3.sensor.exception.not_exist_exception.NotExistErrorLogException;
import live.databo3.sensor.exception.not_exist_exception.SensorNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorTypeNotExistException;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor.repository.SensorRepository;
import live.databo3.sensor.sensor_type.entity.SensorType;
import live.databo3.sensor.sensor_type.repository.SensorTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ErrorLogServiceImpl implements ErrorLogService {
    private final ErrorLogRepository errorLogRepository;
    private final SensorRepository sensorRepository;
    private final SensorTypeRepository sensorTypeRepository;

    public ErrorLogResponseDto createErrorLog(ErrorLogCreateRequestDto errorLogCreateRequestDto) {
        Sensor sensor = sensorRepository.findBySensorSn(errorLogCreateRequestDto.getSensorSn()).orElseThrow(()->new SensorNotExistException(errorLogCreateRequestDto.getSensorSn()));
        SensorType sensorType = sensorTypeRepository.findBySensorType(errorLogCreateRequestDto.getSensorType()).orElseThrow(() -> new SensorTypeNotExistException(errorLogCreateRequestDto.getSensorType()));

        ErrorLog errorLog = errorLogCreateRequestDto.createErrorLog(sensor, sensorType);

        ErrorLog saveErrorLog = errorLogRepository.save(errorLog);

        return ErrorLogResponseDto.builder()
                .logId(saveErrorLog.getLogId())
                .errorMsg(saveErrorLog.getErrorMsg())
                .value(saveErrorLog.getValue())
                .timestamp(saveErrorLog.getTimestamp())
                .sensorType(saveErrorLog.getSensorType())
                .sensor(saveErrorLog.getSensor())
                .build();
    }

    @Override
    public void deleteErrorLog(Long logId) {
        if (!errorLogRepository.existsById(logId)) {
            throw new NotExistErrorLogException();
        }

        errorLogRepository.deleteById(logId);
    }

    @Override
    public List<ErrorLogResponseDto> getErrorLogs(Integer organizationId) {
        return errorLogRepository.getErrorLogs(organizationId).orElse(null);
    }
}
