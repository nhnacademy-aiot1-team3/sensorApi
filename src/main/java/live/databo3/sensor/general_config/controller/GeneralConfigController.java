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
//@RequestMapping("/api/sensor/org/{organizationId}/sensor/{sensorSn}/type/{sensorTypeId}/general")
@RequestMapping("/api/sensor/org/{organizationId}/general")
@RequiredArgsConstructor
public class GeneralConfigController {

    private final GeneralConfigService generalConfigService;

    @PostMapping
    @CheckPermission
    public ResponseEntity<GeneralConfigResponse> createGeneralConfig(@PathVariable Integer organizationId, @RequestParam String sensorSn, @RequestParam Integer sensorTypeId, @RequestBody RegisterGeneralConfigRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(generalConfigService.registerGeneralConfig(organizationId, sensorSn, sensorTypeId, request));
    }

    @PutMapping
    @CheckPermission
    public ResponseEntity<GeneralConfigResponse> modifyGeneralConfig(@PathVariable Integer organizationId, @RequestParam String sensorSn, @RequestParam Integer sensorTypeId, @RequestBody ModifyGeneralConfigRequest request) {
        return ResponseEntity.ok(generalConfigService.modifyGeneralConfig(organizationId, sensorSn, sensorTypeId, request));
    }

    @DeleteMapping
    @CheckPermission
    public ResponseEntity<Void> deleteGeneralConfig(@PathVariable Integer organizationId, @RequestParam String sensorSn, @RequestParam Integer sensorTypeId) {
        generalConfigService.deleteGeneralConfig(organizationId, sensorSn, sensorTypeId);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/all")
    @CheckPermission
    public ResponseEntity<List<GeneralConfigDto>> getAllGeneralConfig(@PathVariable Integer organizationId) {
        return ResponseEntity.ok(generalConfigService.findGeneralConfigByOrganizationId(organizationId));
    }

    @GetMapping
    @CheckPermission
    public ResponseEntity<GeneralConfigResponse> getGeneralConfig(@PathVariable Integer organizationId, @RequestParam String sensorSn, @RequestParam Integer sensorTypeId) {
        return ResponseEntity.ok(generalConfigService.getGeneralConfig(organizationId, sensorSn, sensorTypeId));
    }
}
