package live.databo3.sensor.value_config.service;

import live.databo3.sensor.value_config.dto.ConfigsDto;
import live.databo3.sensor.value_config.dto.ValueConfigDto;
import live.databo3.sensor.value_config.dto.ValueConfigRequest;
import live.databo3.sensor.value_config.dto.ValueConfigResponse;

import java.util.List;

public interface ValueConfigService {
    ValueConfigResponse createValueConfig(Integer organizationId, String sensorSn, Integer sensorTypeId, ValueConfigRequest request);
    ValueConfigResponse modifyValueConfig(Integer organizationId, String sensorSn, Integer sensorTypeId, Long valueConfigNumber, ValueConfigRequest request);
    void deleteValueConfig(Integer organizationId, String sensorSn, Integer sensorTypeId, Long valueConfigNumber);
    ValueConfigResponse getValueConfig(Integer organizationId, String sensorSn, Integer sensorTypeId, Long valueConfigNumber);
    List<ValueConfigDto> getValueConfigListByOrganizationId(Integer organizationId);
    ConfigsDto getConfig(Integer organizationId, String sensorSn, Integer sensorTypeId);
}
