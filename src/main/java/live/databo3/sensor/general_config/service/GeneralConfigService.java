package live.databo3.sensor.general_config.service;

import live.databo3.sensor.general_config.dto.RegisterGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.RegisterTemperatureConfigRequest;
import live.databo3.sensor.general_config.dto.TemperatureConfigDto;

import java.util.List;

public interface GeneralConfigService {
    void registerGeneralConfig(RegisterGeneralConfigRequest request);

    void registerTemperatureConfig(RegisterTemperatureConfigRequest request);

    List<TemperatureConfigDto> findTemperatureConfigByOrganizationName(String name);
}
