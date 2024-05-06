package live.databo3.sensor.general_config.service;

import live.databo3.sensor.general_config.dto.request.ModifyGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.request.RegisterGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.response.GeneralConfigResponse;
import live.databo3.sensor.general_config.entity.GeneralConfig;

import java.util.List;

public interface GeneralConfigService {
    GeneralConfigResponse registerGeneralConfig(Integer organizationId, String sensorSn, Integer sensorTypeId, RegisterGeneralConfigRequest request);

    GeneralConfigResponse modifyGeneralConfig(Integer organizationId, String sensorSn, Integer sensorTypeId, ModifyGeneralConfigRequest request);

    GeneralConfigResponse getGeneralConfig(Integer organizationId, String sensorSn, Integer sensorTypeId);


    List<GeneralConfig> findGeneralConfigByOrganizationId(Integer organizationId);

    void deleteGeneralConfig(Integer organizationId, String sensorSn, Integer sensorTypeId);
}
