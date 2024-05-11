package live.databo3.sensor.error_log.dto;

import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor_type.entity.SensorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ErrorLogResponseDto {
    private Long logId;
    private Sensor sensor;
    private SensorType sensorType;
    private LocalDateTime timestamp;
    private Double value;
    private String errorMsg;
}
