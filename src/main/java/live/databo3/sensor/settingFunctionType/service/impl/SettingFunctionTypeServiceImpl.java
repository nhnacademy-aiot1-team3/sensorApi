package live.databo3.sensor.settingFunctionType.service.impl;

import live.databo3.sensor.exception.not_exist_exception.SettingFunctionTypeNotExistException;
import live.databo3.sensor.settingFunctionType.dto.request.ModifySettingFunctionTypeRequest;
import live.databo3.sensor.settingFunctionType.dto.request.RegisterSettingFunctionTypeRequest;
import live.databo3.sensor.settingFunctionType.entity.SettingFunctionType;
import live.databo3.sensor.settingFunctionType.repository.SettingFunctionTypeRepository;
import live.databo3.sensor.settingFunctionType.service.SettingFunctionTypeService;
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
    public SettingFunctionType modifySettingFunctionType(ModifySettingFunctionTypeRequest request) {
        Long settingFunctionTypeId = request.getFunctionId();

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
