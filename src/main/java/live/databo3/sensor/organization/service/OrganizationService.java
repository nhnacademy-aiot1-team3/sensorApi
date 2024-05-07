package live.databo3.sensor.organization.service;

import live.databo3.sensor.organization.dto.RegisterOrganizationRequest;
import live.databo3.sensor.organization.dto.RegisterOrganizationResponse;

import java.util.List;

public interface OrganizationService {
    RegisterOrganizationResponse registerOrganization(RegisterOrganizationRequest request);
    String findNameById(Integer organizationId);
    List<Integer> findIdList();
    Integer findIdByName(String organizationName);
}
