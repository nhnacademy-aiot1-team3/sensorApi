package live.databo3.sensor.sensor_type.controller;

import live.databo3.sensor.sensor_type.dto.request.ModifySensorTypeRequest;
import live.databo3.sensor.sensor_type.dto.request.RegisterSensorTypeRequest;
import live.databo3.sensor.sensor_type.entity.SensorType;
import live.databo3.sensor.sensor_type.service.SensorTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/sensor/sensorType")
public class SensorTypeController {
    private final SensorTypeService sensorTypeService;

    @GetMapping
    public ResponseEntity<List<SensorType>> getSensorTypeList() {
        return ResponseEntity.ok(sensorTypeService.getSensorTypes());
    }

    @PostMapping
    public ResponseEntity<SensorType> createSensorType(@RequestBody RegisterSensorTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sensorTypeService.registerSensorType(request));
    }

    @PutMapping("/{sensorTypeId}")
    public ResponseEntity<SensorType> modifySensorType(@PathVariable Integer sensorTypeId, @RequestBody ModifySensorTypeRequest request) {
        return ResponseEntity.ok(sensorTypeService.modifySensorType(sensorTypeId, request));
    }

    @DeleteMapping("/{sensorTypeId}")
    public ResponseEntity<Void> deleteSensorType(@PathVariable Integer sensorTypeId) {
        sensorTypeService.deleteSensorTypes(sensorTypeId);
        return ResponseEntity.ok(null);
    }
}
