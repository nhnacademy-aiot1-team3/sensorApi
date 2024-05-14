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

    /**
     * settingFunction 을 등록한다.
     *
     * @since 1.0.0
     */
    public SettingFunctionType registertSettingFunctionType(RegisterSettingFunctionTypeRequest request) {
        SettingFunctionType settingFunctionType = new SettingFunctionType(null, request.getFunctionName());
        return settingFunctionTypeRepository.save(settingFunctionType);
    }

    /**
     * settingFunction 을 수정한다.
     *
     * @since 1.0.0
     */
    @Transactional
    public SettingFunctionType modifySettingFunctionType(Long settingFunctionTypeId, ModifySettingFunctionTypeRequest request) {
        SettingFunctionType settingFunctionType = settingFunctionTypeRepository.findById(settingFunctionTypeId).orElseThrow(() -> new SettingFunctionTypeNotExistException(settingFunctionTypeId));
        settingFunctionType.setFunctionName(request.getFunctionName());
        return settingFunctionType;
    }

    /**
     * settingFunction 목록을 조회한다.
     *
     * @since 1.0.0
     */
    public List<SettingFunctionType> getSettingFunctionTypes() {
        return settingFunctionTypeRepository.findAll();
    }

    /**
     * settingFunction 을 삭제한다.
     *
     * @since 1.0.0
     */
    public void deleteSettingFunctionTypes(Long settingFunctionId) {
        if (!settingFunctionTypeRepository.existsById(settingFunctionId)) {
            throw new SettingFunctionTypeNotExistException(settingFunctionId);
        }
        settingFunctionTypeRepository.deleteById(settingFunctionId);
    }
}
