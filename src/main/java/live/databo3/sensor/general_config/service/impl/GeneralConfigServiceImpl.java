package live.databo3.sensor.general_config.service.impl;

import live.databo3.sensor.annotations.RefreshRedis;
import live.databo3.sensor.exception.already_exist_exception.GeneralConfigAlreadyExistException;
import live.databo3.sensor.exception.not_exist_exception.GeneralConfigNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SettingFunctionTypeNotExistException;
import live.databo3.sensor.general_config.dto.request.modify.ModifyGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.request.register.RegisterGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.response.GeneralConfigResponse;
import live.databo3.sensor.general_config.entity.GeneralConfig;
import live.databo3.sensor.general_config.repository.GeneralConfigRepository;
import live.databo3.sensor.general_config.service.GeneralConfigService;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor.repository.SensorRepository;
import live.databo3.sensor.settingFunctionType.entity.SettingFunctionType;
import live.databo3.sensor.settingFunctionType.repository.SettingFunctionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneralConfigServiceImpl implements GeneralConfigService {
    private final GeneralConfigRepository generalConfigRepository;
    private final SensorRepository sensorRepository;
    private final SettingFunctionTypeRepository settingFunctionTypeRepository;

    @RefreshRedis
    public GeneralConfigResponse registerGeneralConfig(RegisterGeneralConfigRequest request) {
        String sensorSn = request.getSensorSn();
        if (generalConfigRepository.existsBySensorSn(sensorSn)) {
            throw new GeneralConfigAlreadyExistException(sensorSn);
        }
        Sensor sensor = sensorRepository.findById(sensorSn).orElseThrow(() -> new SensorNotExistException(sensorSn));

        Long functionId = request.getFunctionId();
        SettingFunctionType settingFunctionType = settingFunctionTypeRepository.findById(functionId).orElseThrow(() -> new SettingFunctionTypeNotExistException(functionId));

        GeneralConfig generalConfig = new GeneralConfig(null, sensor, settingFunctionType, LocalDateTime.now());

        return (generalConfigRepository.save(generalConfig)).toDto();
    }

    @RefreshRedis
    @Transactional
    public GeneralConfigResponse modifyGeneralConfig(ModifyGeneralConfigRequest request) {
        Long configId = request.getConfigId();
        GeneralConfig generalConfig = generalConfigRepository.findById(configId).orElseThrow(() -> new GeneralConfigNotExistException(configId));

        Long functionId = request.getFunctionId();
        SettingFunctionType settingFunctionType = settingFunctionTypeRepository.findById(functionId).orElseThrow(() -> new SettingFunctionTypeNotExistException(functionId));

        generalConfig.setSettingFunctionType(settingFunctionType);
        generalConfig.setLastUpdateDate(LocalDateTime.now());
        return generalConfig.toDto();
    }

    public String getOrganizationNameByConfigId(Long configId) {
        return generalConfigRepository.findOrganizationNameByConfigId(configId);
    }

    public List<GeneralConfig> findGeneralConfigByOrganizationName(String name) {
        return generalConfigRepository.findAllBySensor_Organization_OrganizationName(name);
    }

    public GeneralConfigResponse getGeneralConfig(Long configId) {
        return generalConfigRepository.findById(configId).orElseThrow(() -> new GeneralConfigNotExistException(configId)).toDto();
    }

    public void deleteGeneralConfig(Long configId) {
        if (!generalConfigRepository.existsById(configId)) {
            throw new GeneralConfigNotExistException(configId);
        }
        generalConfigRepository.deleteById(configId);
    }
}
