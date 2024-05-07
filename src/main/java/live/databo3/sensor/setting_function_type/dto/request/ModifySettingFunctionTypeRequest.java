package live.databo3.sensor.setting_function_type.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifySettingFunctionTypeRequest {
    Long functionId;
    String functionName;
}
