package live.databo3.sensor.value_config.controller;

import live.databo3.sensor.value_config.dto.ValueConfigRequest;
import live.databo3.sensor.value_config.dto.ValueConfigResponse;
import live.databo3.sensor.value_config.service.ValueConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/sensor/org/{organizationId}/sensor/{sensorSn}/type/{sensorTypeId}/value")
public class ValueConfigController {
    private final ValueConfigService valueConfigService;

    @PostMapping
    public ResponseEntity<ValueConfigResponse> createValueConfig(@PathVariable Integer organizationId, @PathVariable String sensorSn, @PathVariable Integer sensorTypeId, @RequestBody ValueConfigRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(valueConfigService.createValueConfig(organizationId, sensorSn, sensorTypeId, request));
    }

    @PutMapping("/{valueConfigNumber}")
    public ResponseEntity<ValueConfigResponse> modifyValueConfig(@PathVariable Integer organizationId, @PathVariable String sensorSn, @PathVariable Integer sensorTypeId, @PathVariable Long valueConfigNumber, @RequestBody ValueConfigRequest request) {
        return ResponseEntity.ok(valueConfigService.modifyValueConfig(organizationId, sensorSn, sensorTypeId, valueConfigNumber, request));
    }

    // todo
    @GetMapping
    public ResponseEntity<?> getValueConfigs(@PathVariable Integer organizationId, @PathVariable String sensorSn, @PathVariable Integer sensorTypeId) {
        if (sensorSn.equals("all")) {
            return ResponseEntity.ok(valueConfigService.getValueConfigListByOrganizationId(organizationId));
        }
        else return null;
    }

    @GetMapping("/{valueConfigNumber}")
    public ResponseEntity<ValueConfigResponse> getValueConfig(@PathVariable Integer organizationId, @PathVariable String sensorSn, @PathVariable Integer sensorTypeId, @PathVariable Long valueConfigNumber) {
        return ResponseEntity.ok(valueConfigService.getValueConfig(organizationId, sensorSn, sensorTypeId, valueConfigNumber));
    }

    @DeleteMapping("/{valueConfigNumber}")
    public ResponseEntity<Void> deleteValueConfig(@PathVariable Integer organizationId, @PathVariable String sensorSn, @PathVariable Integer sensorTypeId, @PathVariable Long valueConfigNumber) {
        valueConfigService.deleteValueConfig(organizationId, sensorSn, sensorTypeId, valueConfigNumber);
        return ResponseEntity.ok(null);
    }
}
