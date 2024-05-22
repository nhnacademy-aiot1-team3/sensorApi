package live.databo3.sensor.value_config.controller;

import live.databo3.sensor.value_config.dto.ConfigsDto;
import live.databo3.sensor.value_config.service.ValueConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sensor/org/{organizationId}/sensor/{sensorSn}/sensorType/{sensorTypeId}/config")
public class ConfigController {
    private final ValueConfigService valueConfigService;

    @GetMapping
    public ResponseEntity<ConfigsDto> getConfig(@PathVariable Integer organizationId, @PathVariable String sensorSn, @PathVariable Integer sensorTypeId) {
        return ResponseEntity.ok(valueConfigService.getConfig(organizationId, sensorSn, sensorTypeId));
    }
}
