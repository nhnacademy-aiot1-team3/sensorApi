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

/**
 * generalConfig entity 관련 controller
 * uri 중 organizationId는 조직의 Integer 타입의 id에 해당한다.
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@RestController
@RequestMapping("/api/sensor/org/{organizationId}")
@RequiredArgsConstructor
public class GeneralConfigController {

    private final GeneralConfigService generalConfigService;

    /**
     * POST 요청을 받아 generalConfig 를 등록한다.
     * @since 1.0.0
     */
    @PostMapping("sensor/{sensorSn}/sensorType/{sensorTypeId}/general")
    @CheckPermission
    public ResponseEntity<GeneralConfigResponse> createGeneralConfig(@PathVariable Integer organizationId, @PathVariable String sensorSn, @PathVariable Integer sensorTypeId, @RequestBody RegisterGeneralConfigRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(generalConfigService.registerGeneralConfig(organizationId, sensorSn, sensorTypeId, request));
    }

    /**
     * PUT 요청을 받아 generalConfig 를 등록한다.
     * @since 1.0.0
     */
    @PutMapping("sensor/{sensorSn}/sensorType/{sensorTypeId}/general")
    @CheckPermission
    public ResponseEntity<GeneralConfigResponse> modifyGeneralConfig(@PathVariable Integer organizationId, @PathVariable String sensorSn, @PathVariable Integer sensorTypeId, @RequestBody ModifyGeneralConfigRequest request) {
        return ResponseEntity.ok(generalConfigService.modifyGeneralConfig(organizationId, sensorSn, sensorTypeId, request));
    }

    /**
     * Delete 요청을 받아 generalConfig 를 삭제한다.
     * @since 1.0.0
     */
    @DeleteMapping("sensor/{sensorSn}/sensorType/{sensorTypeId}/general")
    @CheckPermission
    public ResponseEntity<Void> deleteGeneralConfig(@PathVariable Integer organizationId, @PathVariable String sensorSn, @PathVariable Integer sensorTypeId) {
        generalConfigService.deleteGeneralConfig(organizationId, sensorSn, sensorTypeId);
        return ResponseEntity.ok(null);
    }

    /**
     * Get 요청을 받아 특정 조직에 해당하는 generalConfig 를 조회한다.
     * @since 1.0.0
     */
    @GetMapping("/general")
    @CheckPermission
    public ResponseEntity<List<GeneralConfigDto>> getAllGeneralConfig(@PathVariable Integer organizationId) {
        return ResponseEntity.ok(generalConfigService.findGeneralConfigByOrganizationId(organizationId));
    }

    /**
     * Get 요청을 받아 generalConfig 를 조회한다.
     * @since 1.0.0
     */
    @GetMapping("sensor/{sensorSn}/sensorType/{sensorTypeId}/general")
    @CheckPermission
    public ResponseEntity<GeneralConfigResponse> getGeneralConfig(@PathVariable Integer organizationId, @PathVariable String sensorSn, @PathVariable Integer sensorTypeId) {
        return ResponseEntity.ok(generalConfigService.getGeneralConfig(organizationId, sensorSn, sensorTypeId));
    }
}
