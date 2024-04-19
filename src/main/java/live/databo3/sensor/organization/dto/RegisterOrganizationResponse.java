package live.databo3.sensor.organization.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterOrganizationResponse {
    private Integer organizationId;
    private String organizationName;
}
