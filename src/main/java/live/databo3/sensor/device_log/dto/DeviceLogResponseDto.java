package live.databo3.sensor.device_log.dto;

import live.databo3.sensor.device.entity.Device;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class DeviceLogResponseDto {
    private Long logId;
    private Device device;
    private LocalDateTime timestamp;
    private Double value;
}
