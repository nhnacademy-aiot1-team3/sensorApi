package live.databo3.sensor.organization.service;

public interface OrganizationService {
    String findNameById(Integer organizationId);
    Integer findIdByName(String organizationName);
}
