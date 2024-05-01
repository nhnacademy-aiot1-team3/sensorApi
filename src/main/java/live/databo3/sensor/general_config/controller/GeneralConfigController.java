package live.databo3.sensor.general_config.controller;

import live.databo3.sensor.general_config.dto.request.modify.ModifyGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.request.register.RegisterGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.response.GeneralConfigResponse;
import live.databo3.sensor.general_config.entity.GeneralConfig;
import live.databo3.sensor.general_config.service.GeneralConfigService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sensor/generalConfig")
@RequiredArgsConstructor
public class GeneralConfigController {

    private final GeneralConfigService generalConfigService;

    @PostMapping("/register")
    public ResponseEntity<GeneralConfigResponse> createGeneralConfig(@RequestBody RegisterGeneralConfigRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(generalConfigService.registerGeneralConfig(request));
    }

    @PostMapping("/modify")
    public ResponseEntity<GeneralConfigResponse> modifyGeneralConfig(@RequestBody ModifyGeneralConfigRequest request) {
        return ResponseEntity.ok(generalConfigService.modifyGeneralConfig(request));
    }

    @DeleteMapping("/{configId}")
    public ResponseEntity<Void> deleteGeneralConfig(@PathVariable Long configId) {
        generalConfigService.deleteGeneralConfig(configId);
        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<List<GeneralConfigResponse>> getGeneralConfigByOrganizationName(@RequestParam String organizationName) {
        List<GeneralConfig> generalConfigList = generalConfigService.findGeneralConfigByOrganizationName(organizationName);
        List<GeneralConfigResponse> generalConfigResponseList = new ArrayList<>();
        for (GeneralConfig generalConfig : generalConfigList) {
            generalConfigResponseList.add(generalConfig.toDto());
        }
        return ResponseEntity.ok(generalConfigResponseList);
    }

    @GetMapping("/{configId}")
    public ResponseEntity<GeneralConfigResponse> getGeneralConfig(@PathVariable Long configId) {
        return ResponseEntity.ok(generalConfigService.getGeneralConfig(configId));
    }
}
