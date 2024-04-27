package live.databo3.sensor.sensor.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifySensorRequest {
    String sensorSn;
    Integer sensorTypeId;
    Integer organizationId;
    String sensorName;
    String sensorPlace;
}
