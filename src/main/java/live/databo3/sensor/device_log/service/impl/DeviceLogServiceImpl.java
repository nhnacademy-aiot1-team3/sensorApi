package live.databo3.sensor.device_log.service.impl;

import live.databo3.sensor.device.entity.Device;
import live.databo3.sensor.device.repository.DeviceRepository;
import live.databo3.sensor.device_log.dto.DeviceLogCreateRequestDto;
import live.databo3.sensor.device_log.dto.DeviceLogResponseDto;
import live.databo3.sensor.device_log.entity.DeviceLog;
import live.databo3.sensor.device_log.repository.DeviceLogRepository;
import live.databo3.sensor.device_log.service.DeviceLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceLogServiceImpl implements DeviceLogService {

    private final DeviceLogRepository deviceLogRepository;
    private final DeviceRepository deviceRepository;


    @Override
    public DeviceLogResponseDto createDeviceLog(DeviceLogCreateRequestDto deviceLogCreateRequestDto) {
        Device device = deviceRepository.findByDeviceSn(deviceLogCreateRequestDto.getDeviceSn()).orElse(null);

        DeviceLog deviceLog = deviceLogCreateRequestDto.createDeviceLog(device);

        DeviceLog saveDeviceLog = deviceLogRepository.save(deviceLog);
        return DeviceLogResponseDto.builder()
                .logId(saveDeviceLog.getLogId())
                .value(saveDeviceLog.getValue())
                .timestamp(saveDeviceLog.getTimestamp())
                .device(saveDeviceLog.getDevice())
                .build();
    }

    @Override
    public List<DeviceLogResponseDto> getDeviceLogs(Integer organizationId) {
        return deviceLogRepository.getDeviceLogs(organizationId).orElse(null);
    }
}
