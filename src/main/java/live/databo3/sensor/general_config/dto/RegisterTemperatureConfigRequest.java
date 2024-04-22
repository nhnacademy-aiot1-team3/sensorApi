package live.databo3.sensor.general_config.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterTemperatureConfigRequest implements SensorConfigRequest {
    Long configId;
    double targetValue;
    double deviationValue;
}
