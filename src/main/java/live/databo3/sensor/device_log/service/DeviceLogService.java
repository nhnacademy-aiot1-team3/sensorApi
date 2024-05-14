package live.databo3.sensor.device_log.service;

import live.databo3.sensor.device_log.dto.DeviceLogCreateRequestDto;
import live.databo3.sensor.device_log.dto.DeviceLogResponseDto;

import java.util.List;

public interface DeviceLogService {

    DeviceLogResponseDto createDeviceLog(DeviceLogCreateRequestDto deviceLogCreateRequestDto);

    List<DeviceLogResponseDto> getDeviceLogs(Integer organizationId);
}
