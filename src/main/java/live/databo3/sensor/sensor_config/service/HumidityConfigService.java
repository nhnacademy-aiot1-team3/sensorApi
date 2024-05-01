package live.databo3.sensor.sensor_config.service;

import live.databo3.sensor.sensor_config.dto.request.modify.ModifyHumidityConfigRequest;
import live.databo3.sensor.sensor_config.dto.request.register.RegisterHumidityConfigRequest;
import live.databo3.sensor.sensor_config.dto.response.HumidityConfigResponse;
import live.databo3.sensor.sensor_config.entity.HumidityConfig;

import java.util.List;

public interface HumidityConfigService {

    HumidityConfigResponse registerHumidityConfig(RegisterHumidityConfigRequest request);

    HumidityConfigResponse modifyHumidityConfig(ModifyHumidityConfigRequest request);

    void deleteHumidityConfig(Long configId);

    HumidityConfigResponse getHumidityConfig(Long configId);

    List<HumidityConfig> findHumidityConfigByOrganizationName(String name);

}
