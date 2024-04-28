package live.databo3.sensor.general_config.dto.request.modify;

import live.databo3.sensor.general_config.dto.request.GeneralConfigUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyGeneralConfigRequest implements GeneralConfigUpdateRequest {
    Long configId;
    Long functionId;
}
