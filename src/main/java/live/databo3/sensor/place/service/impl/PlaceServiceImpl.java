package live.databo3.sensor.place.service.impl;

import live.databo3.sensor.exception.already_exist_exception.PlaceAlreadyExistException;
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

/**
 * place entity 관련 service
 * CRUD 와 더불어 알맞은 조직의 device 를 요청했는지 쿼리를 통해 무결성을 검증하는 역할을 포함한다.
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final OrganizationRepository organizationRepository;
    private final PlaceRepository placeRepository;

    /**
     * 이미 존재하는 place 인지 확인 한 후에 없다면 request 의 body 를 통해 생성한다.
     * @since 1.0.0
     */
    public PlaceResponse registerPlace(Integer organizationId, PlaceRequest request) {
        String placeName = request.getPlaceName();

        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new OrganizationNotExistException(organizationId));
        if (placeRepository.existsByPlaceNameAndOrganization_OrganizationId(request.getPlaceName(), organizationId)) {
            throw new PlaceAlreadyExistException(request.getPlaceName());
        }

        Place place = new Place(null, placeName, organization);

        return placeRepository.save(place).toDto();
    }

    /**
     * 이미 존재하는 place 인지 확인 한 후에 없다면 request 의 body 를 통해 수정한다.
     * @since 1.0.0
     */
    @Transactional
    public PlaceResponse modifyPlace(Integer organizationId, Integer placeId, PlaceRequest request) {
        String placeName = request.getPlaceName();
        if (placeRepository.existsByPlaceNameAndOrganization_OrganizationId(request.getPlaceName(), organizationId)) {
            throw new PlaceAlreadyExistException(request.getPlaceName());
        }

        Place place = placeRepository.findByPlaceIdAndOrganization_OrganizationId(placeId, organizationId).orElseThrow(() -> new PlaceNotExistException(placeId));

        place.setPlaceName(placeName);

        return place.toDto();
    }

    /**
     * place 를 조회한다.
     * @since 1.0.0
     */
    public List<PlaceDto> getPlaces(Integer organizationId) {
        return placeRepository.findAllByOrganization_OrganizationId(organizationId);
    }

    /**
     * 존재하는 place 인지 확인 한 후에 존재한다면 request 의 body 를 통해 삭제한다.
     * @since 1.0.0
     */
    @Transactional
    public void deletePlace(Integer organizationId, Integer placeId) {
        if (!placeRepository.existsByPlaceIdAndOrganization_OrganizationId(placeId, organizationId)) {
            throw new PlaceNotExistException(placeId);
        }
        placeRepository.deleteById(placeId);
    }
}
