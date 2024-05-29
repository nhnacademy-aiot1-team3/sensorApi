package live.databo3.sensor.value_config.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConfigsDto {
    private Long functionId;
    private String functionName;
    private String deviceSn;
    private String deviceName;
    private List<ValueEntryDto> value;
}
