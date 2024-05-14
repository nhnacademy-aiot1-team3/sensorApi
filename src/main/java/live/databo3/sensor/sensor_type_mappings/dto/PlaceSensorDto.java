package live.databo3.sensor.sensor_type_mappings.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PlaceSensorDto {
    Integer placeId;
    String placeName;
    List<SensorNameIdDto> sensors;
}
