package live.databo3.sensor.place.controller;

import live.databo3.sensor.place.dto.PlaceDto;
import live.databo3.sensor.place.dto.PlaceRequest;
import live.databo3.sensor.place.dto.PlaceResponse;
import live.databo3.sensor.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensor/org/{organizationId}/place")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @PostMapping
    public ResponseEntity<PlaceResponse> createPlace(@PathVariable Integer organizationId, @RequestBody PlaceRequest placeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(placeService.registerPlace(organizationId, placeRequest));
    }

    @GetMapping
    public ResponseEntity<List<PlaceDto>> getPlaceList(@PathVariable Integer organizationId) {
        return ResponseEntity.ok(placeService.getPlaces(organizationId));
    }

    @PutMapping("/{placeId}")
    public ResponseEntity<PlaceResponse> modifyPlace(@PathVariable Integer organizationId, @PathVariable Integer placeId , @RequestBody PlaceRequest request) {
        return ResponseEntity.ok(placeService.modifyPlace(organizationId, placeId, request));
    }

    @DeleteMapping("/{placeId}")
    public ResponseEntity<Void> deletePlace(@PathVariable Integer organizationId, @PathVariable Integer placeId) {
        placeService.deletePlace(organizationId, placeId);
        return ResponseEntity.ok(null);
    }
}
