package live.databo3.sensor.general_config.service;

import live.databo3.sensor.general_config.dto.request.modify.ModifyGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.request.register.RegisterGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.response.GeneralConfigResponse;
import live.databo3.sensor.general_config.entity.GeneralConfig;

import java.util.List;

public interface GeneralConfigService {
    GeneralConfigResponse registerGeneralConfig(RegisterGeneralConfigRequest request);

    GeneralConfigResponse modifyGeneralConfig(ModifyGeneralConfigRequest request);

    GeneralConfigResponse getGeneralConfig(Long configId);

    String getOrganizationNameByConfigId(Long configId);

    List<GeneralConfig> findGeneralConfigByOrganizationName(String name);

    void deleteGeneralConfig(Long configId);
}
