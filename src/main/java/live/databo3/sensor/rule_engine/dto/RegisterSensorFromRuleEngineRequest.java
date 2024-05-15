package live.databo3.sensor.rule_engine.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterSensorFromRuleEngineRequest {
    private String site;
    private String branch;
    private String place;
    private String device;
    private String endpoint;
    private String type;
    private String phase;
    private String description;
    private String topic;
}
