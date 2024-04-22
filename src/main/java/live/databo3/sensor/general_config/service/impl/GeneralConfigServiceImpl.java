package live.databo3.sensor.general_config.service.impl;

import live.databo3.sensor.annotations.RefreshRedis;
import live.databo3.sensor.general_config.dto.*;
import live.databo3.sensor.general_config.entity.GeneralConfig;
import live.databo3.sensor.general_config.entity.HumidityConfig;
import live.databo3.sensor.general_config.entity.TemperatureConfig;
import live.databo3.sensor.general_config.repository.GeneralConfigRepository;
import live.databo3.sensor.general_config.repository.HumidityConfigRepository;
import live.databo3.sensor.general_config.repository.TemperatureConfigRepository;
import live.databo3.sensor.general_config.service.GeneralConfigService;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor.repository.SensorRepository;
import live.databo3.sensor.settingFunctionType.entity.SettingFunctionType;
import live.databo3.sensor.settingFunctionType.repository.SettingFunctionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneralConfigServiceImpl implements GeneralConfigService {
    private final GeneralConfigRepository generalConfigRepository;
    private final SensorRepository sensorRepository;
    private final SettingFunctionTypeRepository settingFunctionTypeRepository;
    private final TemperatureConfigRepository temperatureConfigRepository;
    private final HumidityConfigRepository humidityConfigRepository;


    @RefreshRedis
    public RegisterGeneralConfigResponse registerGeneralConfig(RegisterGeneralConfigRequest request) {
        Sensor sensor = sensorRepository.findById(request.getSensorSn()).orElseThrow(() -> new RuntimeException("no sensor"));
        SettingFunctionType settingFunctionType = settingFunctionTypeRepository.findById(request.getFunctionId()).orElseThrow(() -> new RuntimeException("no functionId"));

        GeneralConfig generalConfig = new GeneralConfig(null, sensor, settingFunctionType, null);

        return (generalConfigRepository.save(generalConfig)).toRegisterResponse();
    }

    @RefreshRedis
    @Transactional
    public void modifyGeneralConfig(ModifyGeneralConfigRequest request) {
        if (!generalConfigRepository.existsById(request.getConfigId())) {
            throw new RuntimeException("no config");
        }
        GeneralConfig generalConfig = generalConfigRepository.findById(request.getConfigId()).orElseThrow(()->new RuntimeException("general config not exist"));
        SettingFunctionType settingFunctionType = settingFunctionTypeRepository.findById(request.getFunctionId()).orElseThrow(() -> new RuntimeException("no functionId"));

        generalConfig.setSettingFunctionType(settingFunctionType);
    }

    // todo dirty exception
    @RefreshRedis
    public void registerTemperatureConfig(RegisterTemperatureConfigRequest request) {
        GeneralConfig generalConfig = generalConfigRepository.findById(request.getConfigId()).orElseThrow(() -> new RuntimeException("no config"));
        if (temperatureConfigRepository.existsById(request.getConfigId())) {
            throw new RuntimeException("temp config already exist");
        }

        TemperatureConfig temperatureConfig = new TemperatureConfig();
        temperatureConfig.setTargetValue(request.getTargetValue());
        temperatureConfig.setDeviationValue(request.getDeviationValue());
        temperatureConfig.setGeneralConfig(generalConfig);

        temperatureConfigRepository.save(temperatureConfig);
    }

    @RefreshRedis
    @Transactional
    public void modifyTemperatureConfig(RegisterTemperatureConfigRequest request) {
        if (!temperatureConfigRepository.existsById(request.getConfigId())) {
            throw new RuntimeException("temp config not exist");
        }

        TemperatureConfig temperatureConfig = temperatureConfigRepository.findById(request.getConfigId()).orElseThrow(() -> new RuntimeException("no config"));
        temperatureConfig.setTargetValue(request.getTargetValue());
        temperatureConfig.setDeviationValue(request.getDeviationValue());
    }

    @RefreshRedis
    public void deleteTemperatureConfig(DeleteSensorConfigRequest request) {
        if (!temperatureConfigRepository.existsById(request.getConfigId())) {
            throw new RuntimeException("temp config not exist");
        }
        temperatureConfigRepository.deleteById(request.getConfigId());
    }

    // todo dirty exception
    @RefreshRedis
    public void registerHumidityConfig(RegisterHumidityConfigRequest request) {
        GeneralConfig generalConfig = generalConfigRepository.findById(request.getConfigId()).orElseThrow(() -> new RuntimeException("no config"));

        if (humidityConfigRepository.existsById(request.getConfigId())) {
            throw new RuntimeException("hum config already exist");
        }

        HumidityConfig humidityConfig = new HumidityConfig();
        humidityConfig.setTargetValue(request.getTargetValue());
        humidityConfig.setDeviationValue(request.getDeviationValue());
        humidityConfig.setGeneralConfig(generalConfig);

        humidityConfigRepository.save(humidityConfig);
    }

    @RefreshRedis
    @Transactional
    public void modifyHumidityConfig(RegisterHumidityConfigRequest request) {
        if (!humidityConfigRepository.existsById(request.getConfigId())) {
            throw new RuntimeException("hum config not exist");
        }

        HumidityConfig humidityConfig = humidityConfigRepository.findById(request.getConfigId()).orElseThrow(() -> new RuntimeException("no config"));
        humidityConfig.setTargetValue(request.getTargetValue());
        humidityConfig.setDeviationValue(request.getDeviationValue());
    }

    @RefreshRedis
    public void deleteHumidityConfig(DeleteSensorConfigRequest request) {
        if (!humidityConfigRepository.existsById(request.getConfigId())) {
            throw new RuntimeException("hum config not exist");
        }
        humidityConfigRepository.deleteById(request.getConfigId());
    }

    public String getOrganizationNameByConfigId(Long configId) {
        return generalConfigRepository.findOrganizationNameByConfigId(configId);
    }

    public List<GeneralConfig> findGeneralConfigByOrganizationName(String name) {
        return generalConfigRepository.findAllBySensor_Organization_OrganizationName(name);
    }

    public List<TemperatureConfig> findTemperatureConfigByOrganizationName(String name) {
        return temperatureConfigRepository.findAllByGeneralConfig_Sensor_Organization_OrganizationName(name);
    }

    public List<HumidityConfig> findHumidityConfigByOrganizationName(String name) {
        return humidityConfigRepository.findAllByGeneralConfig_Sensor_Organization_OrganizationName(name);
    }
}
