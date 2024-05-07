package live.databo3.sensor.organization.service.impl;

import live.databo3.sensor.exception.OrganizationAlreadyExists;
import live.databo3.sensor.exception.not_exist_exception.OrganizationNameNotExistException;
import live.databo3.sensor.exception.not_exist_exception.OrganizationNotExistException;
import live.databo3.sensor.organization.dto.RegisterOrganizationRequest;
import live.databo3.sensor.organization.dto.RegisterOrganizationResponse;
import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.organization.repository.OrganizationRepository;
import live.databo3.sensor.organization.service.OrganizationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public RegisterOrganizationResponse registerOrganization(RegisterOrganizationRequest request) {
        if (organizationRepository.findByOrganizationName(request.getOrganizationName()).isPresent()) {
            throw new OrganizationAlreadyExists("organization already exists " + request.getOrganizationName());
        }

        return organizationRepository.save(new Organization(null, request.getOrganizationName(), null, null)).toRegisterResponse();
    }

    public String findNameById(Integer organizationId) {
        return organizationRepository.findOrganizationNameByOrganizationId(organizationId).orElseThrow(() -> new OrganizationNotExistException(organizationId)).getOrganizationName();
    }

    public Integer findIdByName(String organizationName) {
        return organizationRepository.findOrganizationIdByOrganizationName(organizationName).orElseThrow(() -> new OrganizationNameNotExistException(organizationName)).getOrganizationId();
    }

    public List<Integer> findIdList() {
        return organizationRepository.getAllIds();
    }
}
