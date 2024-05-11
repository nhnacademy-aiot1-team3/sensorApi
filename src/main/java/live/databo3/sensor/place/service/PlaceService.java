package live.databo3.sensor.place.service;

import live.databo3.sensor.place.dto.PlaceDto;
import live.databo3.sensor.place.dto.PlaceRequest;
import live.databo3.sensor.place.dto.PlaceResponse;

import java.util.List;

public interface PlaceService {
    PlaceResponse registerPlace(Integer organizationId, PlaceRequest request);
    PlaceResponse modifyPlace(Integer organizationId, Integer placeId, PlaceRequest request);
    List<PlaceDto> getPlaces(Integer organizationId);
    void deletePlace(Integer organizationId, Integer placeId);
}
