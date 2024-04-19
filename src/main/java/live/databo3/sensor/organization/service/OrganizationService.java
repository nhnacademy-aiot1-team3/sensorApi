package live.databo3.sensor.organization.service;

import live.databo3.sensor.organization.dto.RegisterOrganizationRequest;
import live.databo3.sensor.organization.dto.RegisterOrganizationResponse;

public interface OrganizationService {
    RegisterOrganizationResponse registerOrganization(RegisterOrganizationRequest request);
}
