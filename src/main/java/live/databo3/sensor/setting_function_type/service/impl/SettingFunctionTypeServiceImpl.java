package live.databo3.sensor.setting_function_type.service.impl;

import live.databo3.sensor.setting_function_type.entity.SettingFunctionType;
import live.databo3.sensor.setting_function_type.repository.SettingFunctionTypeRepository;
import live.databo3.sensor.setting_function_type.service.SettingFunctionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * settingFunction entity 관련 service
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Service
@RequiredArgsConstructor
public class SettingFunctionTypeServiceImpl implements SettingFunctionTypeService {
    private final SettingFunctionTypeRepository settingFunctionTypeRepository;

    public List<SettingFunctionType> getSettingFunctionTypes() {
        return settingFunctionTypeRepository.findAll();
    }
}
