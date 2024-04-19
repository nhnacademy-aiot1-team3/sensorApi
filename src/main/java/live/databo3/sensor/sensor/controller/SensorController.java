package live.databo3.sensor.sensor.controller;

import live.databo3.sensor.sensor.dto.RegisterSensorRequest;
import live.databo3.sensor.sensor.dto.RegisterSensorResponse;
import live.databo3.sensor.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensor/sensor")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @PostMapping("/register")
    public ResponseEntity<RegisterSensorResponse> createOrganization(@RequestBody RegisterSensorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sensorService.registerSensor(request));
    }

}
