package live.databo3.sensor.organization.service.impl;

import live.databo3.sensor.exception.not_exist_exception.OrganizationNameNotExistException;
import live.databo3.sensor.exception.not_exist_exception.OrganizationNotExistException;
import live.databo3.sensor.organization.repository.OrganizationRepository;
import live.databo3.sensor.organization.service.OrganizationService;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public String findNameById(Integer organizationId) {
        return organizationRepository.findOrganizationNameByOrganizationId(organizationId).orElseThrow(() -> new OrganizationNotExistException(organizationId)).getOrganizationName();
    }

    public Integer findIdByName(String organizationName) {
        return organizationRepository.findOrganizationIdByOrganizationName(organizationName).orElseThrow(() -> new OrganizationNameNotExistException(organizationName)).getOrganizationId();
    }
}
