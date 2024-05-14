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

/**
 * 특정 키값의 redis 갱신 요청이 들어왔을 때, redis 에 특정 키 값의 데이터를 넣어줍니다.
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@RestController
@RequestMapping("/api/sensor/org/{organizationId}/sensor")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    /**
     * POST 요청을 받아 sensor 를 등록합니다.
     * @since 1.0.0
     */
    @PostMapping
    public ResponseEntity<SensorResponse> createSensor(@PathVariable Integer organizationId, @RequestBody RegisterSensorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sensorService.registerSensor(organizationId, request));
    }

    /**
     * GET 요청을 받아 특정 조직의 센서리스트를 조회합니다.
     * @since 1.0.0
     */
    @GetMapping
    public ResponseEntity<List<SensorDto>> getSensorList(@PathVariable Integer organizationId) {
        return ResponseEntity.ok(sensorService.getSensors(organizationId));
    }

    /**
     * GET 요청을 받아 sensor 를 조회합니다.
     * @since 1.0.0
     */
    @GetMapping("/{sensorSn}")
    public ResponseEntity<SensorDto> getSensor(@PathVariable Integer organizationId, @PathVariable String sensorSn) {
        return ResponseEntity.ok(sensorService.getSensor(organizationId, sensorSn));
    }

    /**
     * PUT 요청을 받아 sensor 를 수정합니다.
     * @since 1.0.0
     */
    @PutMapping({"/{sensorSn}"})
    public ResponseEntity<SensorResponse> modifySensor(@PathVariable Integer organizationId, @PathVariable String sensorSn, @RequestBody ModifySensorRequest request) {
        return ResponseEntity.ok(sensorService.modifySensor(organizationId, sensorSn, request));
    }

    /**
     * DELETE 요청을 받아 sensor 를 삭제합니다.
     * @since 1.0.0
     */
    @DeleteMapping("/{sensorSn}")
    public ResponseEntity<Void> deleteSensor(@PathVariable Integer organizationId, @PathVariable String sensorSn) {
        sensorService.deleteSensor(organizationId, sensorSn);
        return ResponseEntity.ok(null);
    }
}
