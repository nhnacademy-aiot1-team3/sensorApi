package live.databo3.sensor.settingFunctionType.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifySettingFunctionTypeRequest {
    Long functionId;
    String functionName;
}
