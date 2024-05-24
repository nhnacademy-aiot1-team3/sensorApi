package live.databo3.sensor.sensor_type_mappings.controller;

import live.databo3.sensor.sensor_type_mappings.dto.ModifySensorTypeMappingRequest;
import live.databo3.sensor.sensor_type_mappings.dto.SensorTypeMappingListDto;
import live.databo3.sensor.sensor_type_mappings.dto.SensorTypeMappingResponse;
import live.databo3.sensor.sensor_type_mappings.service.SensorTypeMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping
public class SensorTypeMappingController {
    private final SensorTypeMappingService sensorTypeMappingService;

    /**
     * POST 요청을 받아 sensorTypeMapping 을 등록한다.
     * @since 1.0.0
     */
    @PostMapping("/api/sensor/org/{organizationId}/sensor/{sensorSn}/sensorTypeId/{sensorTypeId}/sensorTypeMapping")
    public ResponseEntity<SensorTypeMappingResponse> registerSensorTypeMapping(@PathVariable String sensorSn, @PathVariable Integer organizationId, @PathVariable Integer sensorTypeId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sensorTypeMappingService.registerSensorTypeMapping(sensorSn, organizationId, sensorTypeId));
    }

    /**
     * PUT 요청을 받아 sensorTypeMapping 을 수정한다.
     * @since 1.0.0
     */
    @PutMapping("/api/sensor/org/{organizationId}/sensor/{sensorSn}/sensorTypeId/{sensorTypeId}/sensorTypeMapping")
    public ResponseEntity<SensorTypeMappingResponse> modifySensorTypeMapping(@PathVariable String sensorSn, @PathVariable Integer organizationId, @PathVariable Integer sensorTypeId, @RequestBody ModifySensorTypeMappingRequest request) {
        return ResponseEntity.ok(sensorTypeMappingService.modifySensorTypeMapping(sensorSn, organizationId, sensorTypeId, request));
    }

    /**
     * GET 요청을 받아 sensorTypeMapping 을 조회한다.
     * @since 1.0.0
     */
    @GetMapping("/api/sensor/org/{organizationId}/sensor/{sensorSn}/sensorTypeId/{sensorTypeId}/sensorTypeMapping")
    public ResponseEntity<SensorTypeMappingResponse> getSensorTypeMapping(@PathVariable String sensorSn, @PathVariable Integer organizationId, @PathVariable Integer sensorTypeId) {
        return ResponseEntity.ok(sensorTypeMappingService.getSensorTypeMapping(sensorSn, organizationId, sensorTypeId));
    }

    /**
     * DELETE 요청을 받아 sensorTypeMapping 을 삭제한다.
     * @since 1.0.0
     */
    @DeleteMapping("/api/sensor/org/{organizationId}/sensor/{sensorSn}/sensorTypeId/{sensorTypeId}/sensorTypeMapping")
    public ResponseEntity<Void> deleteSensorTypeMapping(@PathVariable String sensorSn, @PathVariable Integer organizationId, @PathVariable Integer sensorTypeId) {
        sensorTypeMappingService.deleteSensorTypeMapping(sensorSn, organizationId, sensorTypeId);
        return ResponseEntity.ok(null);
    }

    /**
     * GET 요청을 받아 특정 조직의 sensorTypeMapping 을 조회한다.
     * @since 1.0.0
     */
    @GetMapping("/api/sensor/org/{organizationId}/sensorTypeMapping")
    public ResponseEntity<List<SensorTypeMappingListDto>> getSensorTypeMappingList(@PathVariable Integer organizationId) {
        return ResponseEntity.ok(sensorTypeMappingService.getSensorTypeMappingList(organizationId));
    }
}
