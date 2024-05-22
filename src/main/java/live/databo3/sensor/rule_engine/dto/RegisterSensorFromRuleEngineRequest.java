package live.databo3.sensor.rule_engine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
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
