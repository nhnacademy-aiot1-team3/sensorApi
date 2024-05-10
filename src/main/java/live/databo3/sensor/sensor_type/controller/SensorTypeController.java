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

/**
 * 특정 키값의 redis 갱신 요청이 들어왔을 때, redis 에 특정 키 값의 데이터를 넣어줍니다.
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/sensor/sensorType")
public class SensorTypeController {
    private final SensorTypeService sensorTypeService;

    /**
     * GET 요청을 받아 sensorType 리스트를 조회한다.
     * @since 1.0.0
     */
    @GetMapping
    public ResponseEntity<List<SensorType>> getSensorTypeList() {
        return ResponseEntity.ok(sensorTypeService.getSensorTypes());
    }

    /**
     * POST 요청을 받아 sensorType 을 등록한다.
     * @since 1.0.0
     */
    @PostMapping
    public ResponseEntity<SensorType> createSensorType(@RequestBody RegisterSensorTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sensorTypeService.registerSensorType(request));
    }

    /**
     * PUT 요청을 받아 sensorType 을 등록한다.
     * @since 1.0.0
     */
    @PutMapping("/{sensorTypeId}")
    public ResponseEntity<SensorType> modifySensorType(@PathVariable Integer sensorTypeId, @RequestBody ModifySensorTypeRequest request) {
        return ResponseEntity.ok(sensorTypeService.modifySensorType(sensorTypeId, request));
    }

    /**
     * DELETE 요청을 받아 sensorType 을 삭제한다.
     * @since 1.0.0
     */
    @DeleteMapping("/{sensorTypeId}")
    public ResponseEntity<Void> deleteSensorType(@PathVariable Integer sensorTypeId) {
        sensorTypeService.deleteSensorTypes(sensorTypeId);
        return ResponseEntity.ok(null);
    }
}
