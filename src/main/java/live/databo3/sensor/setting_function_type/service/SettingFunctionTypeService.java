package live.databo3.sensor.setting_function_type.service;

import live.databo3.sensor.setting_function_type.dto.request.ModifySettingFunctionTypeRequest;
import live.databo3.sensor.setting_function_type.dto.request.RegisterSettingFunctionTypeRequest;
import live.databo3.sensor.setting_function_type.entity.SettingFunctionType;

import java.util.List;

public interface SettingFunctionTypeService {
    SettingFunctionType registertSettingFunctionType(RegisterSettingFunctionTypeRequest request);
    SettingFunctionType modifySettingFunctionType(ModifySettingFunctionTypeRequest request);
    List<SettingFunctionType> getSettingFunctionTypes();
    void deleteSettingFunctionTypes(Long settingFunctionId);
}
