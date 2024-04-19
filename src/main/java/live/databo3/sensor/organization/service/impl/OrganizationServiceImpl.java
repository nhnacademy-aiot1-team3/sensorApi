package live.databo3.sensor.organization.service.impl;

import live.databo3.sensor.exception.OrganizationAlreadyExists;
import live.databo3.sensor.organization.dto.RegisterOrganizationRequest;
import live.databo3.sensor.organization.dto.RegisterOrganizationResponse;
import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.organization.repository.OrganizationRepository;
import live.databo3.sensor.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    public RegisterOrganizationResponse registerOrganization(RegisterOrganizationRequest request) {
        if (organizationRepository.findByOrganizationName(request.getOrganizationName()).isPresent()) {
            throw new OrganizationAlreadyExists("organization already exists " + request.getOrganizationName());
        }

        return organizationRepository.save(new Organization(null, request.getOrganizationName(), null, null)).toRegisterResponse();
    }
}
