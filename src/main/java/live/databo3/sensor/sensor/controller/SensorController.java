package live.databo3.sensor.sensor.controller;

import live.databo3.sensor.sensor.dto.SensorDto;
import live.databo3.sensor.sensor.dto.request.ModifySensorRequest;
import live.databo3.sensor.sensor.dto.request.RegisterSensorRequest;
import live.databo3.sensor.sensor.dto.SensorResponse;
import live.databo3.sensor.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensor/org/{organizationId}/sensor")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @PostMapping
    public ResponseEntity<SensorResponse> createSensor(@PathVariable Integer organizationId, @RequestBody RegisterSensorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sensorService.registerSensor(organizationId, request));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SensorDto>> getSensorList(@PathVariable Integer organizationId) {
        return ResponseEntity.ok(sensorService.getSensors(organizationId));
    }

    @GetMapping("/{sensorSn}")
    public ResponseEntity<SensorDto> getSensor(@PathVariable Integer organizationId, @PathVariable String sensorSn) {
        return ResponseEntity.ok(sensorService.getSensor(organizationId, sensorSn));
    }

    @PutMapping({"/{sensorSn}"})
    public ResponseEntity<SensorResponse> modifySensor(@PathVariable Integer organizationId, @PathVariable String sensorSn, @RequestBody ModifySensorRequest request) {
        return ResponseEntity.ok(sensorService.modifySensor(organizationId, sensorSn, request));
    }

    @DeleteMapping("/{sensorSn}")
    public ResponseEntity<Void> deleteSensor(@PathVariable Integer organizationId, @PathVariable String sensorSn) {
        sensorService.deleteSensor(organizationId, sensorSn);
        return ResponseEntity.ok(null);
    }
}
