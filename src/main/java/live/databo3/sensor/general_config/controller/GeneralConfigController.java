package live.databo3.sensor.general_config.controller;

import live.databo3.sensor.general_config.dto.RegisterGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.RegisterGeneralConfigResponse;
import live.databo3.sensor.general_config.dto.RegisterHumidityConfigRequest;
import live.databo3.sensor.general_config.dto.RegisterTemperatureConfigRequest;
import live.databo3.sensor.general_config.entity.TemperatureConfig;
import live.databo3.sensor.general_config.service.GeneralConfigService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensor")
@RequiredArgsConstructor
public class GeneralConfigController {

    private final GeneralConfigService generalConfigService;

    @PostMapping("/generalConfig/register")
    public ResponseEntity<RegisterGeneralConfigResponse> createGeneralConfig(@RequestBody RegisterGeneralConfigRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(generalConfigService.registerGeneralConfig(request));
    }

    @PostMapping("/temperatureConfig/register")
    public ResponseEntity<Void> createTemperatureConfig(@RequestBody RegisterTemperatureConfigRequest request) {
        generalConfigService.registerTemperatureConfig(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PostMapping("/humidityConfig/register")
    public ResponseEntity<Void> createHumidityConfig(@RequestBody RegisterHumidityConfigRequest request) {
        generalConfigService.registerHumidityConfig(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/config/{organizationName}")
    public ResponseEntity<List<TemperatureConfig>> getConfigByOrganizationName(@PathVariable String organizationName) {
        return ResponseEntity.ok().body(generalConfigService.findTemperatureConfigByOrganizationName(organizationName));
    }

}
