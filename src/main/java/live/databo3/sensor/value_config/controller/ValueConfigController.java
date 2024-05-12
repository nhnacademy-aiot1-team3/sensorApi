package live.databo3.sensor.value_config.controller;

import live.databo3.sensor.annotations.CheckPermission;
import live.databo3.sensor.value_config.dto.ValueConfigDto;
import live.databo3.sensor.value_config.dto.ValueConfigRequest;
import live.databo3.sensor.value_config.dto.ValueConfigResponse;
import live.databo3.sensor.value_config.service.ValueConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sensor/org/{organizationId}/value")

public class ValueConfigController {
    private final ValueConfigService valueConfigService;

    @PostMapping
    @CheckPermission
    public ResponseEntity<ValueConfigResponse> createValueConfig(@PathVariable Integer organizationId, @RequestParam String sensorSn, @RequestParam Integer sensorTypeId, @RequestBody ValueConfigRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(valueConfigService.createValueConfig(organizationId, sensorSn, sensorTypeId, request));
    }

    @PutMapping("/{valueConfigNumber}")
    @CheckPermission
    public ResponseEntity<ValueConfigResponse> modifyValueConfig(@PathVariable Integer organizationId, @RequestParam String sensorSn, @RequestParam Integer sensorTypeId, @PathVariable Long valueConfigNumber, @RequestBody ValueConfigRequest request) {
        return ResponseEntity.ok(valueConfigService.modifyValueConfig(organizationId, sensorSn, sensorTypeId, valueConfigNumber, request));
    }

    @GetMapping
    @CheckPermission
    public ResponseEntity<List<ValueConfigDto>> getValueConfigs(@PathVariable Integer organizationId) {
        return ResponseEntity.ok(valueConfigService.getValueConfigListByOrganizationId(organizationId));
    }

    @GetMapping("/{valueConfigNumber}")
    @CheckPermission
    public ResponseEntity<ValueConfigResponse> getValueConfig(@PathVariable Integer organizationId, @RequestParam String sensorSn, @RequestParam Integer sensorTypeId, @PathVariable Long valueConfigNumber) {
        return ResponseEntity.ok(valueConfigService.getValueConfig(organizationId, sensorSn, sensorTypeId, valueConfigNumber));
    }

    @DeleteMapping("/{valueConfigNumber}")
    @CheckPermission
    public ResponseEntity<Void> deleteValueConfig(@PathVariable Integer organizationId, @RequestParam String sensorSn, @RequestParam Integer sensorTypeId, @PathVariable Long valueConfigNumber) {
        valueConfigService.deleteValueConfig(organizationId, sensorSn, sensorTypeId, valueConfigNumber);
        return ResponseEntity.ok(null);
    }
}
