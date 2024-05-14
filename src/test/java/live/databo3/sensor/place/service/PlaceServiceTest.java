package live.databo3.sensor.place.service;

import live.databo3.sensor.exception.not_exist_exception.OrganizationNotExistException;
import live.databo3.sensor.exception.not_exist_exception.PlaceNotExistException;
import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.organization.repository.OrganizationRepository;
import live.databo3.sensor.place.dto.PlaceRequest;
import live.databo3.sensor.place.dto.PlaceResponse;
import live.databo3.sensor.place.entity.Place;
import live.databo3.sensor.place.repository.PlaceRepository;
import live.databo3.sensor.place.service.impl.PlaceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {
    @Mock
    private OrganizationRepository organizationRepository;
    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private PlaceServiceImpl placeService;

    Organization organization;
    Place place;
    PlaceResponse placeResponse;
    PlaceRequest placeRequest;

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

        placeResponse = place.toDto();

        placeRequest = new PlaceRequest();
        ReflectionTestUtils.setField(placeRequest, "placeName", "testPlace");
    }

    @Test
    void placeCreateSuccessTest() {
        when(organizationRepository.findById(anyInt())).thenReturn(Optional.of(organization));
        when(placeRepository.save(any(Place.class))).thenReturn(place);

        PlaceResponse response = placeService.registerPlace(1, placeRequest);

        assertEquals(response.getPlaceId(), placeResponse.getPlaceId());
        assertEquals(response.getPlaceName(), placeResponse.getPlaceName());
    }

    @Test
    void placeCreateFailTest() {
        when(organizationRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> placeService.registerPlace(1, placeRequest))
                .isInstanceOf(OrganizationNotExistException.class)
                .hasMessageContaining(OrganizationNotExistException.MESSAGE);
    }

    @Test
    void placeModifySuccessTest() {
        when(placeRepository.findByPlaceIdAndOrganization_OrganizationId(anyInt(), anyInt())).thenReturn(Optional.of(place));

        PlaceResponse response = placeService.modifyPlace(1, 1, placeRequest);
        assertEquals(response.getPlaceId(), placeResponse.getPlaceId());
        assertEquals(response.getPlaceName(), placeResponse.getPlaceName());

        verify(placeRepository, times(1)).findByPlaceIdAndOrganization_OrganizationId(anyInt(), anyInt());
    }

    @Test
    void placeModifyFailTest() {
        when(placeRepository.findByPlaceIdAndOrganization_OrganizationId(anyInt(), anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> placeService.modifyPlace(1, 1, placeRequest))
                .isInstanceOf(PlaceNotExistException.class)
                .hasMessageContaining(PlaceNotExistException.MESSAGE);
    }

    @Test
    void placeListGetTest() {
        placeService.getPlaces(1);

        verify(placeRepository, times(1)).findAllByOrganization_OrganizationId(anyInt());
    }

    @Test
    void placeDeleteSuccessTest() {
        when(placeRepository.existsByPlaceIdAndOrganization_OrganizationId(anyInt(), anyInt())).thenReturn(true);

        placeService.deletePlace(1, 1);

        verify(placeRepository, times(1)).existsByPlaceIdAndOrganization_OrganizationId(anyInt(), anyInt());
    }

    @Test
    void placeDeleteFailTest() {
        when(placeRepository.existsByPlaceIdAndOrganization_OrganizationId(anyInt(), anyInt())).thenReturn(false);

        assertThatThrownBy(() -> placeService.deletePlace(1,1))
                .isInstanceOf(PlaceNotExistException.class)
                .hasMessageContaining(PlaceNotExistException.MESSAGE);
    }
}
