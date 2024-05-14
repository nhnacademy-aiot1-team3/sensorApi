package live.databo3.sensor.sensor.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterSensorRequest {
    private String sensorSn;
    private String sensorName;
    private Integer placeId;
}
