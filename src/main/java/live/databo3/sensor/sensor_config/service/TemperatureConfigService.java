package live.databo3.sensor.sensor_config.service;

import live.databo3.sensor.sensor_config.dto.request.modify.ModifyTemperatureConfigRequest;
import live.databo3.sensor.sensor_config.dto.request.register.RegisterTemperatureConfigRequest;
import live.databo3.sensor.sensor_config.dto.response.TemperatureConfigResponse;
import live.databo3.sensor.sensor_config.entity.TemperatureConfig;

import java.util.List;

public interface TemperatureConfigService {

    TemperatureConfigResponse registerTemperatureConfig(RegisterTemperatureConfigRequest request);

    TemperatureConfigResponse modifyTemperatureConfig(ModifyTemperatureConfigRequest request);

    void deleteTemperatureConfig(Long configId);

    TemperatureConfigResponse getTemperatureConfig(Long configId);

    List<TemperatureConfig> findTemperatureConfigByOrganizationName(String name);
}
