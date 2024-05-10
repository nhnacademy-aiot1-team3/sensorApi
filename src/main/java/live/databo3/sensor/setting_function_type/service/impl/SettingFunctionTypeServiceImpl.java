package live.databo3.sensor.setting_function_type.service.impl;

import live.databo3.sensor.exception.not_exist_exception.SettingFunctionTypeNotExistException;
import live.databo3.sensor.setting_function_type.dto.request.ModifySettingFunctionTypeRequest;
import live.databo3.sensor.setting_function_type.dto.request.RegisterSettingFunctionTypeRequest;
import live.databo3.sensor.setting_function_type.entity.SettingFunctionType;
import live.databo3.sensor.setting_function_type.repository.SettingFunctionTypeRepository;
import live.databo3.sensor.setting_function_type.service.SettingFunctionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingFunctionTypeServiceImpl implements SettingFunctionTypeService {
    private final SettingFunctionTypeRepository settingFunctionTypeRepository;

    public SettingFunctionType registertSettingFunctionType(RegisterSettingFunctionTypeRequest request) {
        SettingFunctionType settingFunctionType = new SettingFunctionType(null, request.getFunctionName());
        return settingFunctionTypeRepository.save(settingFunctionType);
    }

    @Transactional
    public SettingFunctionType modifySettingFunctionType(Long settingFunctionTypeId, ModifySettingFunctionTypeRequest request) {
        SettingFunctionType settingFunctionType = settingFunctionTypeRepository.findById(settingFunctionTypeId).orElseThrow(() -> new SettingFunctionTypeNotExistException(settingFunctionTypeId));
        settingFunctionType.setFunctionName(request.getFunctionName());
        return settingFunctionType;
    }

    public List<SettingFunctionType> getSettingFunctionTypes() {
        return settingFunctionTypeRepository.findAll();
    }

    public void deleteSettingFunctionTypes(Long settingFunctionId) {
        if (!settingFunctionTypeRepository.existsById(settingFunctionId)) {
            throw new SettingFunctionTypeNotExistException(settingFunctionId);
        }
        settingFunctionTypeRepository.deleteById(settingFunctionId);
    }
}
