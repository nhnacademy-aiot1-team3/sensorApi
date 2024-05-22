package live.databo3.sensor.general_config.controller;

import live.databo3.sensor.annotations.CheckPermission;
import live.databo3.sensor.general_config.dto.GeneralConfigDto;
import live.databo3.sensor.general_config.dto.request.ModifyGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.request.RegisterGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.response.GeneralConfigResponse;
import live.databo3.sensor.general_config.service.GeneralConfigService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensor/org/{organizationId}")
@RequiredArgsConstructor
public class GeneralConfigController {

    private final GeneralConfigService generalConfigService;

    @PostMapping("sensor/{sensorSn}/sensorType/{sensorTypeId}/general")
    @CheckPermission
    public ResponseEntity<GeneralConfigResponse> createGeneralConfig(@PathVariable Integer organizationId, @PathVariable String sensorSn, @PathVariable Integer sensorTypeId, @RequestBody RegisterGeneralConfigRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(generalConfigService.registerGeneralConfig(organizationId, sensorSn, sensorTypeId, request));
    }

    @PutMapping("sensor/{sensorSn}/sensorType/{sensorTypeId}/general")
    @CheckPermission
    public ResponseEntity<GeneralConfigResponse> modifyGeneralConfig(@PathVariable Integer organizationId, @PathVariable String sensorSn, @PathVariable Integer sensorTypeId, @RequestBody ModifyGeneralConfigRequest request) {
        return ResponseEntity.ok(generalConfigService.modifyGeneralConfig(organizationId, sensorSn, sensorTypeId, request));
    }

    @DeleteMapping("sensor/{sensorSn}/sensorType/{sensorTypeId}/general")
    @CheckPermission
    public ResponseEntity<Void> deleteGeneralConfig(@PathVariable Integer organizationId, @PathVariable String sensorSn, @PathVariable Integer sensorTypeId) {
        generalConfigService.deleteGeneralConfig(organizationId, sensorSn, sensorTypeId);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/general")
    @CheckPermission
    public ResponseEntity<List<GeneralConfigDto>> getAllGeneralConfig(@PathVariable Integer organizationId) {
        return ResponseEntity.ok(generalConfigService.findGeneralConfigByOrganizationId(organizationId));
    }

    @GetMapping("sensor/{sensorSn}/sensorType/{sensorTypeId}/general")
    @CheckPermission
    public ResponseEntity<GeneralConfigResponse> getGeneralConfig(@PathVariable Integer organizationId, @PathVariable String sensorSn, @PathVariable Integer sensorTypeId) {
        return ResponseEntity.ok(generalConfigService.getGeneralConfig(organizationId, sensorSn, sensorTypeId));
    }
}
