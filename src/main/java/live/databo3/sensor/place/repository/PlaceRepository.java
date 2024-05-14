package live.databo3.sensor.place.repository;

import live.databo3.sensor.place.dto.PlaceDto;
import live.databo3.sensor.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Integer> {
    Optional<Place> findByPlaceIdAndOrganization_OrganizationId(Integer placeId, Integer organizationId);
    List<PlaceDto> findAllByOrganization_OrganizationId(Integer organizationId);
    boolean existsByPlaceIdAndOrganization_OrganizationId(Integer placeId, Integer organizationId);
}
