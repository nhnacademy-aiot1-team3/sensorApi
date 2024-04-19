package live.databo3.sensor.general_config.service.impl;

import live.databo3.sensor.general_config.dto.RegisterGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.RegisterTemperatureConfigRequest;
import live.databo3.sensor.general_config.dto.TemperatureConfigDto;
import live.databo3.sensor.general_config.entity.GeneralConfig;
import live.databo3.sensor.general_config.entity.TemperatureConfig;
import live.databo3.sensor.general_config.repository.GeneralConfigRepository;
import live.databo3.sensor.general_config.repository.TemperatureConfigRepository;
import live.databo3.sensor.general_config.service.GeneralConfigService;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor.repository.SensorRepository;
import live.databo3.sensor.settingFunctionType.entity.SettingFunctionType;
import live.databo3.sensor.settingFunctionType.repository.SettingFunctionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.management.RuntimeMBeanException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneralConfigServiceImpl implements GeneralConfigService {
    private final GeneralConfigRepository generalConfigRepository;
    private final SensorRepository sensorRepository;
    private final SettingFunctionTypeRepository settingFunctionTypeRepository;

    private final TemperatureConfigRepository temperatureConfigRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    public void registerGeneralConfig(RegisterGeneralConfigRequest request) {
        Sensor sensor = sensorRepository.findById(request.getSensorSn()).orElseThrow(() -> new RuntimeException("no sensor"));
        SettingFunctionType settingFunctionType = settingFunctionTypeRepository.findById(request.getFunctionId()).orElseThrow(() -> new RuntimeException("no functionId"));

        GeneralConfig generalConfig = new GeneralConfig(null, sensor, settingFunctionType, null);

        generalConfigRepository.save(generalConfig);
    }

    public void registerTemperatureConfig(RegisterTemperatureConfigRequest request) {
        GeneralConfig generalConfig = generalConfigRepository.findById(request.getConfigId()).orElseThrow(() -> new RuntimeException("no config"));

        TemperatureConfig temperatureConfig = new TemperatureConfig();
        temperatureConfig.setTargetValue(request.getTargetValue());
        temperatureConfig.setDeviationValue(request.getDeviationValue());
        temperatureConfig.setGeneralConfig(generalConfig);

        if (temperatureConfigRepository.existsById(request.getConfigId())) {
            throw new RuntimeException("setting Already exists");
        }
        temperatureConfigRepository.save(temperatureConfig);
    }

    public List<TemperatureConfigDto> findTemperatureConfigByOrganizationName(String name) {
        return temperatureConfigRepository.findAllByGeneralConfig_Sensor_Organization_OrganizationName(name);
    }


}
