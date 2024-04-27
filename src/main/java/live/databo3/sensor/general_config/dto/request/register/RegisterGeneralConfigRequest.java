package live.databo3.sensor.general_config.dto.request.register;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterGeneralConfigRequest {
    String sensorSn;
    Long functionId;
}
