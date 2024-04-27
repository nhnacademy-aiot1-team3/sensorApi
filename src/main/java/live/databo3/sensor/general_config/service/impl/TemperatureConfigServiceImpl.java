package live.databo3.sensor.general_config.service.impl;

import live.databo3.sensor.annotations.RefreshRedis;
import live.databo3.sensor.exception.already_exist_exception.TemperatureConfigAlreadyExistException;
import live.databo3.sensor.exception.not_exist_exception.GeneralConfigNotExistException;
import live.databo3.sensor.exception.not_exist_exception.TemperatureConfigNotExistException;
import live.databo3.sensor.general_config.dto.request.delete.DeleteSensorConfigRequest;
import live.databo3.sensor.general_config.dto.request.register.RegisterTemperatureConfigRequest;
import live.databo3.sensor.general_config.dto.response.TemperatureConfigResponse;
import live.databo3.sensor.general_config.entity.GeneralConfig;
import live.databo3.sensor.general_config.entity.TemperatureConfig;
import live.databo3.sensor.general_config.repository.GeneralConfigRepository;
import live.databo3.sensor.general_config.repository.TemperatureConfigRepository;
import live.databo3.sensor.general_config.service.TemperatureConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemperatureConfigServiceImpl implements TemperatureConfigService {
    private final GeneralConfigRepository generalConfigRepository;
    private final TemperatureConfigRepository temperatureConfigRepository;

    @RefreshRedis
    public TemperatureConfigResponse registerTemperatureConfig(RegisterTemperatureConfigRequest request) {
        Long configId = request.getConfigId();
        if (temperatureConfigRepository.existsById(configId)) {
            throw new TemperatureConfigAlreadyExistException(configId);
        }

        GeneralConfig generalConfig = generalConfigRepository.findById(configId).orElseThrow(() -> new GeneralConfigNotExistException(configId));
        TemperatureConfig temperatureConfig = new TemperatureConfig();
        temperatureConfig.setTargetValue(request.getTargetValue());
        temperatureConfig.setDeviationValue(request.getDeviationValue());
        temperatureConfig.setGeneralConfig(generalConfig);

        return temperatureConfigRepository.save(temperatureConfig).toDto();
    }

    @RefreshRedis
    @Transactional
    public TemperatureConfigResponse modifyTemperatureConfig(RegisterTemperatureConfigRequest request) {
        Long configId = request.getConfigId();
        TemperatureConfig temperatureConfig = temperatureConfigRepository.findById(configId).orElseThrow(() -> new TemperatureConfigNotExistException(configId));
        temperatureConfig.setTargetValue(request.getTargetValue());
        temperatureConfig.setDeviationValue(request.getDeviationValue());
        return temperatureConfig.toDto();
    }

    public TemperatureConfigResponse getTemperatureConfig(Long configId) {
        return temperatureConfigRepository.findById(configId).orElseThrow(() -> new TemperatureConfigNotExistException(configId)).toDto();
    }

    @RefreshRedis
    public void deleteTemperatureConfig(Long configId) {
        if (!temperatureConfigRepository.existsById(configId)) {
            throw new TemperatureConfigNotExistException(configId);
        }
        temperatureConfigRepository.deleteById(configId);
    }

    public List<TemperatureConfig> findTemperatureConfigByOrganizationName(String name) {
        return temperatureConfigRepository.findAllByGeneralConfig_Sensor_Organization_OrganizationName(name);
    }
}
