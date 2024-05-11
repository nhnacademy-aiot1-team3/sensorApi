package live.databo3.sensor.device_log.dto;

import live.databo3.sensor.device.entity.Device;
import live.databo3.sensor.device_log.entity.DeviceLog;
import live.databo3.sensor.sensor.entity.Sensor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DeviceLogCreateRequestDto {
    private String deviceSn;
    private Double value;

    public DeviceLog createDeviceLog(Device device) {
        return DeviceLog.builder()
                .device(device)
                .timestamp(LocalDateTime.now())
                .value(value)
                .build();
    }
}
