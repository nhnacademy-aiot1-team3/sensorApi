package live.databo3.sensor.general_config.dto.request.delete;

import live.databo3.sensor.general_config.dto.request.SensorConfigRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteSensorConfigRequest implements SensorConfigRequest {
    Long configId;
}
