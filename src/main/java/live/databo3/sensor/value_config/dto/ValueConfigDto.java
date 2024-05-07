package live.databo3.sensor.value_config.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ValueConfigDto {
    private String sensorSn;
    private String sensorType;
    private String firstEntry;
    private String secondEntry;
}
