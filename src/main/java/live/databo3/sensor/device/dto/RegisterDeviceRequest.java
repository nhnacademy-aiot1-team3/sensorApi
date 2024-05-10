package live.databo3.sensor.device.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterDeviceRequest {
    private String deviceSn;
    private String deviceName;
}
