package live.databo3.sensor.organization.repository;

import live.databo3.sensor.organization.dto.OrganizationId;
import live.databo3.sensor.organization.dto.OrganizationName;
import live.databo3.sensor.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    Optional<Organization> findByOrganizationName(String organizationName);

    Optional<OrganizationName> findOrganizationNameByOrganizationId(Integer organizationId);

    Optional<OrganizationId> findOrganizationIdByOrganizationName(String organizationName);
}
