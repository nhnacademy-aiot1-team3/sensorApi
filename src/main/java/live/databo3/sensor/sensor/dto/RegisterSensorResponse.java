package live.databo3.sensor.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterSensorResponse {
    private String sensorSn;
    private Integer organizationId;
    private Integer sensorTypeId;
    private String sensorName;
    private String sensorPlace;
}

