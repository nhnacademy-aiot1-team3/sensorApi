package live.databo3.sensor.general_config.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralConfigForRedis {
    private String deviceName;
    private String functionName;
}
