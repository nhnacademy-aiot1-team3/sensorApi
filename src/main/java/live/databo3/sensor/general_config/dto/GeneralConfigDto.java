package live.databo3.sensor.general_config.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GeneralConfigDto {
    String sensorSn;
    String sensorType;
    String functionName;
}
