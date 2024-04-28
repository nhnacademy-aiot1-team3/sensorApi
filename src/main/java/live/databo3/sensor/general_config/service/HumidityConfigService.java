package live.databo3.sensor.general_config.service;

import live.databo3.sensor.general_config.dto.request.delete.DeleteSensorConfigRequest;
import live.databo3.sensor.general_config.dto.request.register.RegisterHumidityConfigRequest;
import live.databo3.sensor.general_config.dto.response.HumidityConfigResponse;
import live.databo3.sensor.general_config.entity.HumidityConfig;

import java.util.List;

public interface HumidityConfigService {

    HumidityConfigResponse registerHumidityConfig(RegisterHumidityConfigRequest request);

    HumidityConfigResponse modifyHumidityConfig(RegisterHumidityConfigRequest request);

    void deleteHumidityConfig(Long configId);

    HumidityConfigResponse getHumidityConfig(Long configId);

    List<HumidityConfig> findHumidityConfigByOrganizationName(String name);

}
