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
@RequestMapping("/api/sensor/sensor")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @PostMapping("/register")
    public ResponseEntity<SensorResponse> createSensor(@RequestBody RegisterSensorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sensorService.registerSensor(request));
    }

    @GetMapping
    public ResponseEntity<List<SensorDto>> getSensorList(@RequestParam Integer organizationId) {
        return ResponseEntity.ok(sensorService.getSensors(organizationId));
    }

    @GetMapping("/{sensorSn}")
    public ResponseEntity<SensorDto> getSensor(@PathVariable String sensorSn) {
        return ResponseEntity.ok(sensorService.getSensor(sensorSn));
    }

    @PostMapping("/modify")
    public ResponseEntity<SensorResponse> modifySensor(@RequestBody ModifySensorRequest request) {
        return ResponseEntity.ok(sensorService.modifySensor(request));
    }

    @DeleteMapping("/{sensorSn}")
    public ResponseEntity<Void> deleteSensor(@PathVariable String sensorSn) {
        sensorService.deleteSensor(sensorSn);
        return ResponseEntity.ok(null);
    }
}
