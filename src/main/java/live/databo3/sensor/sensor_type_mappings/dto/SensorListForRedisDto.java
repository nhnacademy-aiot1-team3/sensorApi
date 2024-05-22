package live.databo3.sensor.sensor_type_mappings.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SensorListForRedisDto {
    String sensorSn;
    List<String> sensorTypes;
}
