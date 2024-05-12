package live.databo3.sensor.sensor_type_mappings.controller;

import live.databo3.sensor.sensor_type_mappings.dto.ModifySensorTypeMappingRequest;
import live.databo3.sensor.sensor_type_mappings.dto.SensorTypeMappingResponse;
import live.databo3.sensor.sensor_type_mappings.service.SensorTypeMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * sensorTypeMapping entity 관련 controller
 * uri 중 organizationId는 조직의 Integer 타입의 id에 해당한다.
 * uri 중 sensorSn은 센서의 String 타입의 id에 해당한다.
 * uri 중 sensorTypeId는 센서타입의 Integer 타입의 id에 해당한다.
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sensor/org/{organizationId}/sensorTypeMapping")
public class SensorTypeMappingController {
    private final SensorTypeMappingService sensorTypeMappingService;

    /**
     * POST 요청을 받아 sensorTypeMapping 을 등록한다.
     * @since 1.0.0
     */
    @PostMapping
    public ResponseEntity<SensorTypeMappingResponse> registerSensorTypeMapping(@RequestParam String sensorSn, @PathVariable Integer organizationId, @RequestParam Integer sensorTypeId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sensorTypeMappingService.registerSensorTypeMapping(sensorSn, organizationId, sensorTypeId));
    }

    /**
     * PUT 요청을 받아 sensorTypeMapping 을 수정한다.
     * @since 1.0.0
     */
    @PutMapping
    public ResponseEntity<SensorTypeMappingResponse> modifySensorTypeMapping(@RequestParam String sensorSn, @PathVariable Integer organizationId, @RequestParam Integer sensorTypeId, @RequestBody ModifySensorTypeMappingRequest request) {
        return ResponseEntity.ok(sensorTypeMappingService.modifySensorTypeMapping(sensorSn, organizationId, sensorTypeId, request));
    }

    /**
     * GET 요청을 받아 sensorTypeMapping 을 조회한다.
     * @since 1.0.0
     */
    @GetMapping
    public ResponseEntity<SensorTypeMappingResponse> getSensorTypeMapping(@RequestParam String sensorSn, @PathVariable Integer organizationId, @RequestParam Integer sensorTypeId) {
        return ResponseEntity.ok(sensorTypeMappingService.getSensorTypeMapping(sensorSn, organizationId, sensorTypeId));
    }

    /**
     * DELETE 요청을 받아 sensorTypeMapping 을 삭제한다.
     * @since 1.0.0
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteSensorTypeMapping(@RequestParam String sensorSn, @PathVariable Integer organizationId, @RequestParam Integer sensorTypeId) {
        sensorTypeMappingService.deleteSensorTypeMapping(sensorSn, organizationId, sensorTypeId);
        return ResponseEntity.ok(null);
    }
}
