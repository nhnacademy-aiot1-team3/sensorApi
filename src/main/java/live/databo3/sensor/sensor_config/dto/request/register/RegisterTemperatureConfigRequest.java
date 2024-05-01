package live.databo3.sensor.sensor_config.dto.request.register;

import live.databo3.sensor.sensor_config.dto.request.SensorConfigRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterTemperatureConfigRequest implements SensorConfigRequest {
    Long configId;
    double targetValue;
    double deviationValue;
}
