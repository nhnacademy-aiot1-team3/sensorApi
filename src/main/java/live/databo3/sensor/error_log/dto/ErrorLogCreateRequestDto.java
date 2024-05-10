package live.databo3.sensor.error_log.dto;

import live.databo3.sensor.error_log.entity.ErrorLog;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor_type.entity.SensorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ErrorLogCreateRequestDto {
    private String sensorSn;
    private String sensorType;
    private Double value;
    private String errorMsg;

    public ErrorLog createErrorLog(Sensor sensor, SensorType sensorType) {
        return ErrorLog.builder()
                .sensor(sensor)
                .sensorType(sensorType)
                .value(value)
                .errorMsg(errorMsg)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
