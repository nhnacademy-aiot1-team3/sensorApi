package live.databo3.sensor.general_config.dto.request.register;

import live.databo3.sensor.general_config.dto.request.SensorConfigRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterHumidityConfigRequest implements SensorConfigRequest {
    Long configId;
    double targetValue;
    double deviationValue;
}
