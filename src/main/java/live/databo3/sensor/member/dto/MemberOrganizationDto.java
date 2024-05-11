package live.databo3.sensor.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberOrganizationDto {
    Integer state;
    List<RoleDto> role;
    Integer organizationId;
    String organizationName;
}
