package live.databo3.sensor.sensor_config.dto.request.modify;

import live.databo3.sensor.sensor_config.dto.request.SensorConfigRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyTemperatureConfigRequest implements SensorConfigRequest {
    Long configId;
    double targetValue;
    double deviationValue;
}
