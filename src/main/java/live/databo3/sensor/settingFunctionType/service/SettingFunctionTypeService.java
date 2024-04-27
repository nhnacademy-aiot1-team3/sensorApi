package live.databo3.sensor.settingFunctionType.service;

import live.databo3.sensor.settingFunctionType.dto.request.ModifySettingFunctionTypeRequest;
import live.databo3.sensor.settingFunctionType.dto.request.RegisterSettingFunctionTypeRequest;
import live.databo3.sensor.settingFunctionType.entity.SettingFunctionType;

import java.util.List;

public interface SettingFunctionTypeService {
    SettingFunctionType registertSettingFunctionType(RegisterSettingFunctionTypeRequest request);
    SettingFunctionType modifySettingFunctionType(ModifySettingFunctionTypeRequest request);
    List<SettingFunctionType> getSettingFunctionTypes();
    void deleteSettingFunctionTypes(Long settingFunctionId);
}
