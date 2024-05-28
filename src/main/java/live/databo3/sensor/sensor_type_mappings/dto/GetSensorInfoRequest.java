package live.databo3.sensor.sensor_type_mappings.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetSensorInfoRequest {
    List<Long> configIdList;
}
