package live.databo3.sensor.sensor_config.controller;

import live.databo3.sensor.sensor_config.dto.request.modify.ModifyHumidityConfigRequest;
import live.databo3.sensor.sensor_config.dto.request.register.RegisterHumidityConfigRequest;
import live.databo3.sensor.sensor_config.dto.response.HumidityConfigResponse;
import live.databo3.sensor.sensor_config.entity.HumidityConfig;
import live.databo3.sensor.sensor_config.service.HumidityConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/sensor/humidityConfig")
@RequiredArgsConstructor
public class HumidityConfigController {

    private final HumidityConfigService humidityConfigService;
    @PostMapping("/register")
    public ResponseEntity<HumidityConfigResponse> createHumidityConfig(@RequestBody RegisterHumidityConfigRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(humidityConfigService.registerHumidityConfig(request));
    }

    @PostMapping("/modify")
    public ResponseEntity<HumidityConfigResponse> modifyHumidityConfig(@RequestBody ModifyHumidityConfigRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(humidityConfigService.modifyHumidityConfig(request));
    }

    @DeleteMapping("/{configId}")
    public ResponseEntity<Void> deleteHumidityConfig(@PathVariable Long configId) {
        humidityConfigService.deleteHumidityConfig(configId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/{configId}")
    public ResponseEntity<HumidityConfigResponse> getHumidityConfig(@PathVariable Long configId) {
        return ResponseEntity.ok(humidityConfigService.getHumidityConfig(configId));
    }

    @GetMapping
    public ResponseEntity<List<HumidityConfig>> getConfigByOrganizationName(@RequestParam String organizationName) {
        return ResponseEntity.ok(humidityConfigService.findHumidityConfigByOrganizationName(organizationName));
    }
}
