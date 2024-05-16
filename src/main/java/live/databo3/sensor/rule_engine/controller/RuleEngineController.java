package live.databo3.sensor.rule_engine.controller;

import live.databo3.sensor.rule_engine.dto.RegisterSensorFromRuleEngineRequest;
import live.databo3.sensor.rule_engine.service.RuleEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class RuleEngineController {
    private final RuleEngineService ruleEngineService;

    @PostMapping("/sensor")
    public ResponseEntity<Void> registerSensor(@RequestBody RegisterSensorFromRuleEngineRequest request) {
        ruleEngineService.registerSensor(request.getBranch(), request.getPlace(), request.getDevice(), request.getEndpoint());
        return ResponseEntity.ok(null);
    }
}
