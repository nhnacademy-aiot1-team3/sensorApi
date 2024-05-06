package live.databo3.sensor.sensor_type_mappings.controller;

import live.databo3.sensor.sensor_type_mappings.dto.ModifySensorTypeMappingRequest;
import live.databo3.sensor.sensor_type_mappings.dto.SensorTypeMappingResponse;
import live.databo3.sensor.sensor_type_mappings.service.SensorTypeMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sensor/org/{organizationId}/sensor/{sensorSn}/type/{sensorTypeId}")
public class SensorTypeMappingController {
    private final SensorTypeMappingService sensorTypeMappingService;

    @PostMapping
    public ResponseEntity<SensorTypeMappingResponse> registerSensorTypeMapping(@PathVariable String sensorSn, @PathVariable Integer organizationId, @PathVariable Integer sensorTypeId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sensorTypeMappingService.registerSensorTypeMapping(sensorSn, organizationId, sensorTypeId));
    }

    @PutMapping
    public ResponseEntity<SensorTypeMappingResponse> modifySensorTypeMapping(@PathVariable String sensorSn, @PathVariable Integer organizationId, @PathVariable Integer sensorTypeId, @RequestBody ModifySensorTypeMappingRequest request) {
        return ResponseEntity.ok(sensorTypeMappingService.modifySensorTypeMapping(sensorSn, organizationId, sensorTypeId, request));
    }

    @GetMapping
    public ResponseEntity<SensorTypeMappingResponse> getSensorTypeMapping(@PathVariable String sensorSn, @PathVariable Integer organizationId, @PathVariable Integer sensorTypeId) {
        return ResponseEntity.ok(sensorTypeMappingService.getSensorTypeMapping(sensorSn, organizationId, sensorTypeId));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSensorTypeMapping(@PathVariable String sensorSn, @PathVariable Integer organizationId, @PathVariable Integer sensorTypeId) {
        sensorTypeMappingService.deleteSensorTypeMapping(sensorSn, organizationId, sensorTypeId);
        return ResponseEntity.ok(null);
    }
}
