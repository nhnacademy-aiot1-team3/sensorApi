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
@RequestMapping("/api/sensor/org/{organizationId}/sensor/{sensorSn}/type/{sensorTypeId}/general")
@RequiredArgsConstructor
public class GeneralConfigController {

    private final GeneralConfigService generalConfigService;

    @PostMapping
    public ResponseEntity<GeneralConfigResponse> createGeneralConfig(@PathVariable Integer organizationId, @PathVariable String sensorSn, @PathVariable Integer sensorTypeId, @RequestBody RegisterGeneralConfigRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(generalConfigService.registerGeneralConfig(organizationId, sensorSn, sensorTypeId, request));
    }

    @PutMapping
    public ResponseEntity<GeneralConfigResponse> modifyGeneralConfig(@PathVariable Integer organizationId, @PathVariable String sensorSn, @PathVariable Integer sensorTypeId, @RequestBody ModifyGeneralConfigRequest request) {
        return ResponseEntity.ok(generalConfigService.modifyGeneralConfig(organizationId, sensorSn, sensorTypeId, request));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteGeneralConfig(@PathVariable Integer organizationId, @PathVariable String sensorSn, @PathVariable Integer sensorTypeId) {
        generalConfigService.deleteGeneralConfig(organizationId, sensorSn, sensorTypeId);
        return ResponseEntity.ok(null);
    }

    // todo 센서 타입 별로 조회 기능 추가할 것
    @GetMapping
    @CheckPermission
    public ResponseEntity<?> getGeneralConfig(@PathVariable Integer organizationId, @PathVariable String sensorSn, @PathVariable Integer sensorTypeId) {
        if (!sensorSn.equals("all")) {
            return ResponseEntity.ok(generalConfigService.getGeneralConfig(organizationId, sensorSn, sensorTypeId));
        }
        List<GeneralConfigDto> generalConfigDtoList = generalConfigService.findGeneralConfigByOrganizationId(organizationId);

        return ResponseEntity.ok(generalConfigDtoList);
    }
}
