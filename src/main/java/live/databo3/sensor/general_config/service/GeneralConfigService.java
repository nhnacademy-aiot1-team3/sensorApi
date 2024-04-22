package live.databo3.sensor.general_config.service;

import live.databo3.sensor.general_config.dto.RegisterGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.RegisterGeneralConfigResponse;
import live.databo3.sensor.general_config.dto.RegisterHumidityConfigRequest;
import live.databo3.sensor.general_config.dto.RegisterTemperatureConfigRequest;
import live.databo3.sensor.general_config.entity.GeneralConfig;
import live.databo3.sensor.general_config.entity.HumidityConfig;
import live.databo3.sensor.general_config.entity.TemperatureConfig;

import java.util.List;

public interface GeneralConfigService {
    RegisterGeneralConfigResponse registerGeneralConfig(RegisterGeneralConfigRequest request);

    void registerTemperatureConfig(RegisterTemperatureConfigRequest request);

    void registerHumidityConfig(RegisterHumidityConfigRequest request);

    String getOrganizationNameByConfigId(Long configId);

    List<GeneralConfig> findGeneralConfigByOrganizationName(String name);

    List<TemperatureConfig> findTemperatureConfigByOrganizationName(String name);

    List<HumidityConfig> findHumidityConfigByOrganizationName(String name);
}
