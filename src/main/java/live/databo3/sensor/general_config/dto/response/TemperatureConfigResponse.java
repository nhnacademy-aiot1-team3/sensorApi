package live.databo3.sensor.general_config.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TemperatureConfigResponse {
    private Long configId;
    private double targetValue;
    private double deviationValue;
}
