package live.databo3.sensor.general_config.service;

import live.databo3.sensor.general_config.dto.request.delete.DeleteSensorConfigRequest;
import live.databo3.sensor.general_config.dto.request.register.RegisterTemperatureConfigRequest;
import live.databo3.sensor.general_config.dto.response.TemperatureConfigResponse;
import live.databo3.sensor.general_config.entity.TemperatureConfig;

import java.util.List;

public interface TemperatureConfigService {

    TemperatureConfigResponse registerTemperatureConfig(RegisterTemperatureConfigRequest request);

    TemperatureConfigResponse modifyTemperatureConfig(RegisterTemperatureConfigRequest request);

    void deleteTemperatureConfig(Long configId);

    TemperatureConfigResponse getTemperatureConfig(Long configId);

    List<TemperatureConfig> findTemperatureConfigByOrganizationName(String name);
}
