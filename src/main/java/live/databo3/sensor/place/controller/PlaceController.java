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

/**
 * place entity 관련 controller
 * CRUD 와 더불어 알맞은 조직의 place 를 요청했는지 쿼리를 통해 무결성을 검증하는 역할을 포함한다.
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@RestController
@RequestMapping("/api/sensor/org/{organizationId}/place")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    /**
     * POST 요청을 받아 place 를 등록한다.
     * @since 1.0.0
     */
    @PostMapping
    public ResponseEntity<PlaceResponse> createPlace(@PathVariable Integer organizationId, @RequestBody PlaceRequest placeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(placeService.registerPlace(organizationId, placeRequest));
    }

    /**
     * GET 요청을 받아 place 를 조회한다.
     * @since 1.0.0
     */
    @GetMapping
    public ResponseEntity<List<PlaceDto>> getPlaceList(@PathVariable Integer organizationId) {
        return ResponseEntity.ok(placeService.getPlaces(organizationId));
    }

    /**
     * PUT 요청을 받아 place 를 수정한다.
     * @since 1.0.0
     */
    @PutMapping("/{placeId}")
    public ResponseEntity<PlaceResponse> modifyPlace(@PathVariable Integer organizationId, @PathVariable Integer placeId , @RequestBody PlaceRequest request) {
        return ResponseEntity.ok(placeService.modifyPlace(organizationId, placeId, request));
    }

    /**
     * DELETE 요청을 받아 place 를 삭제한다.
     * @since 1.0.0
     */
    @DeleteMapping("/{placeId}")
    public ResponseEntity<Void> deletePlace(@PathVariable Integer organizationId, @PathVariable Integer placeId) {
        placeService.deletePlace(organizationId, placeId);
        return ResponseEntity.ok(null);
    }
}
