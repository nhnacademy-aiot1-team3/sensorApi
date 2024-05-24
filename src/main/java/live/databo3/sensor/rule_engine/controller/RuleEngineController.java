package live.databo3.sensor.rule_engine.controller;

import live.databo3.sensor.rule_engine.dto.RegisterSensorFromRuleEngineRequest;
import live.databo3.sensor.rule_engine.service.RuleEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * ruleEngine 에서 보낸 동적으로 센서 추가하는 요청을 받는 api 입니다.
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping
public class RuleEngineController {
    private final RuleEngineService ruleEngineService;

    /**
     * POST 요청을 받아 sensor 를 등록한다.
     * @since 1.0.0
     */
    @PostMapping("/sensor")
    public ResponseEntity<Void> registerSensor(@RequestBody RegisterSensorFromRuleEngineRequest request) {
        ruleEngineService.registerSensor(request.getBranch(), request.getPlace(), request.getDevice(), request.getEndpoint());
        return ResponseEntity.ok(null);
    }
}
