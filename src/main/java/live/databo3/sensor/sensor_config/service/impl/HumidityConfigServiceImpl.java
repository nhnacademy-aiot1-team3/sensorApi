package live.databo3.sensor.sensor_config.service.impl;

import live.databo3.sensor.annotations.RefreshRedis;
import live.databo3.sensor.exception.already_exist_exception.HumidityConfigAlreadyExistException;
import live.databo3.sensor.exception.not_exist_exception.GeneralConfigNotExistException;
import live.databo3.sensor.exception.not_exist_exception.HumidityConfigNotExistException;
import live.databo3.sensor.sensor_config.dto.request.modify.ModifyHumidityConfigRequest;
import live.databo3.sensor.sensor_config.dto.request.register.RegisterHumidityConfigRequest;
import live.databo3.sensor.sensor_config.dto.response.HumidityConfigResponse;
import live.databo3.sensor.general_config.entity.GeneralConfig;
import live.databo3.sensor.sensor_config.entity.HumidityConfig;
import live.databo3.sensor.general_config.repository.GeneralConfigRepository;
import live.databo3.sensor.sensor_config.repository.HumidityConfigRepository;
import live.databo3.sensor.sensor_config.service.HumidityConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HumidityConfigServiceImpl implements HumidityConfigService {
    private final GeneralConfigRepository generalConfigRepository;
    private final HumidityConfigRepository humidityConfigRepository;

    @RefreshRedis
    public HumidityConfigResponse registerHumidityConfig(RegisterHumidityConfigRequest request) {
        Long configId = request.getConfigId();
        if (humidityConfigRepository.existsById(configId)) {
            throw new HumidityConfigAlreadyExistException(configId);
        }
        GeneralConfig generalConfig = generalConfigRepository.findById(configId).orElseThrow(() -> new GeneralConfigNotExistException(configId));
        HumidityConfig humidityConfig = new HumidityConfig();
        humidityConfig.setTargetValue(request.getTargetValue());
        humidityConfig.setDeviationValue(request.getDeviationValue());
        humidityConfig.setGeneralConfig(generalConfig);

        return humidityConfigRepository.save(humidityConfig).toDto();
    }

    @RefreshRedis
    @Transactional
    public HumidityConfigResponse modifyHumidityConfig(ModifyHumidityConfigRequest request) {
        Long configId = request.getConfigId();
        HumidityConfig humidityConfig = humidityConfigRepository.findById(configId).orElseThrow(() -> new HumidityConfigNotExistException(configId));
        humidityConfig.setTargetValue(request.getTargetValue());
        humidityConfig.setDeviationValue(request.getDeviationValue());
        return humidityConfig.toDto();
    }

    public HumidityConfigResponse getHumidityConfig(Long configId) {
        return humidityConfigRepository.findById(configId).orElseThrow(() -> new HumidityConfigNotExistException(configId)).toDto();
    }

    @RefreshRedis
    public void deleteHumidityConfig(Long configId) {
        if (!humidityConfigRepository.existsById(configId)) {
            throw new HumidityConfigNotExistException(configId);
        }
        humidityConfigRepository.deleteById(configId);
    }

    public List<HumidityConfig> findHumidityConfigByOrganizationName(String name) {
        return humidityConfigRepository.findAllByGeneralConfig_Sensor_Organization_OrganizationName(name);
    }
}
