package live.databo3.sensor.general_config.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GeneralConfigResponse {
    private Long configId;
    private String sensorSn;
    private Long functionId;
    private LocalDateTime lastUpdateDate;
}
