package live.databo3.sensor.place.repository;

import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.place.dto.PlaceDto;
import live.databo3.sensor.place.entity.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PlaceRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    PlaceRepository placeRepository;

    Organization organization;
    Place place;

    @BeforeEach
    void setup() {
        organization = Organization.builder()
                .controllerSn("testControllerSn")
                .gatewaySn("testGatewaySn")
                .organizationName("testOrganizationName")
                .build();

        place = Place.builder()
                .placeName("testPlace")
                .organization(organization)
                .build();

        entityManager.persist(organization);
        entityManager.persist(place);
    }

    @Test
    void findByPlaceIdAndOrganization_OrganizationIdTest() {
        Optional<Place> foundPlace = placeRepository.findByPlaceIdAndOrganization_OrganizationId(place.getPlaceId(), organization.getOrganizationId());

        assertTrue(foundPlace.isPresent());
        assertEquals(foundPlace.get(), place);
    }

    @Test
    void findAllByOrganization_OrganizationIdTest() {
        List<PlaceDto> list = placeRepository.findAllByOrganization_OrganizationId(organization.getOrganizationId());

        assertEquals(1, list.size());
        assertEquals(list.get(0).getPlaceId(), place.getPlaceId());
        assertEquals(list.get(0).getPlaceName(), place.getPlaceName());
    }

    @Test
    void existsByPlaceIdAndOrganization_OrganizationIdTest() {
        boolean isExist = placeRepository.existsByPlaceIdAndOrganization_OrganizationId(place.getPlaceId(), organization.getOrganizationId());

        assertTrue(isExist);
    }
}
