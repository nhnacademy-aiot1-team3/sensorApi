package live.databo3.sensor.sensor_type_mappings.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrganizationPlaceDto {
    Integer organizationId;
    String organizationName;
    List<PlaceSensorDto> place;
}
