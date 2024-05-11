package live.databo3.sensor.place.service.impl;

import live.databo3.sensor.exception.not_exist_exception.OrganizationNotExistException;
import live.databo3.sensor.exception.not_exist_exception.PlaceNotExistException;
import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.organization.repository.OrganizationRepository;
import live.databo3.sensor.place.dto.PlaceDto;
import live.databo3.sensor.place.dto.PlaceRequest;
import live.databo3.sensor.place.dto.PlaceResponse;
import live.databo3.sensor.place.entity.Place;
import live.databo3.sensor.place.repository.PlaceRepository;
import live.databo3.sensor.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final OrganizationRepository organizationRepository;
    private final PlaceRepository placeRepository;

    public PlaceResponse registerPlace(Integer organizationId, PlaceRequest request) {
        String placeName = request.getPlaceName();

        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new OrganizationNotExistException(organizationId));

        Place place = new Place(null, placeName, organization);

        return placeRepository.save(place).toDto();
    }

    @Transactional
    public PlaceResponse modifyPlace(Integer organizationId, Integer placeId, PlaceRequest request) {
        String placeName = request.getPlaceName();

        Place place = placeRepository.findByPlaceIdAndOrganization_OrganizationId(placeId, organizationId).orElseThrow(() -> new PlaceNotExistException(placeId));

        place.setPlaceName(placeName);

        return place.toDto();
    }

    public List<PlaceDto> getPlaces(Integer organizationId) {
        return placeRepository.findAllByOrganization_OrganizationId(organizationId);
    }

    @Transactional
    public void deletePlace(Integer organizationId, Integer placeId) {
        if (!placeRepository.existsByPlaceIdAndOrganization_OrganizationId(placeId, organizationId)) {
            throw new PlaceNotExistException(placeId);
        }
        placeRepository.deleteById(placeId);
    }
}
