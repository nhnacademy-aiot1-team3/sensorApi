package live.databo3.sensor.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SensorResponse {
    private String sensorSn;
    private String sensorName;
    private String sensorPlace;
}

