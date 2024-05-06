package live.databo3.sensor.sensor_type_mappings.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SensorTypeMappingResponse {
    private Long recordNumber;
    private String sensorSn;
    private Integer sensorTypeId;
}
