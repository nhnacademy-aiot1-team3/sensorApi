package live.databo3.sensor.sensor_config.controller;

import live.databo3.sensor.sensor_config.dto.request.modify.ModifyTemperatureConfigRequest;
import live.databo3.sensor.sensor_config.dto.request.register.RegisterTemperatureConfigRequest;
import live.databo3.sensor.sensor_config.dto.response.TemperatureConfigResponse;
import live.databo3.sensor.sensor_config.entity.TemperatureConfig;
import live.databo3.sensor.sensor_config.service.TemperatureConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sensor/temperatureConfig")
public class TemperatureConfigController {

    private final TemperatureConfigService temperatureConfigService;

    @PostMapping("/register")
    public ResponseEntity<TemperatureConfigResponse> createTemperatureConfig(@RequestBody RegisterTemperatureConfigRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(temperatureConfigService.registerTemperatureConfig(request));
    }

    @PostMapping("/modify")
    public ResponseEntity<TemperatureConfigResponse> modifyTemperatureConfig(@RequestBody ModifyTemperatureConfigRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(temperatureConfigService.modifyTemperatureConfig(request));
    }

    @DeleteMapping("/{configId}")
    public ResponseEntity<Void> deleteTemperatureConfig(@PathVariable Long configId) {
        temperatureConfigService.deleteTemperatureConfig(configId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/{configId}")
    public ResponseEntity<TemperatureConfigResponse> getTemperatureConfig(@PathVariable Long configId) {
        return ResponseEntity.ok(temperatureConfigService.getTemperatureConfig(configId));
    }

    @GetMapping
    public ResponseEntity<List<TemperatureConfig>> getConfigByOrganizationName(@RequestParam String organizationName) {
        return ResponseEntity.ok((temperatureConfigService.findTemperatureConfigByOrganizationName(organizationName)));
    }
}
