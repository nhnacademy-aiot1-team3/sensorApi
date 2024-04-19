package live.databo3.sensor.general_config.service;

import live.databo3.sensor.general_config.dto.RegisterGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.RegisterTemperatureConfigRequest;

public interface GeneralConfigService {
    void registerGeneralConfig(RegisterGeneralConfigRequest request);

    void registerTemperatureConfig(RegisterTemperatureConfigRequest request);
}
