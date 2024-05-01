package live.databo3.sensor.sensor_config.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HumidityConfigResponse {
    private Long configId;
    private double targetValue;
    private double deviationValue;
}
