package live.databo3.sensor.device.service;

import live.databo3.sensor.device.dto.DeviceDto;
import live.databo3.sensor.device.dto.DeviceResponse;
import live.databo3.sensor.device.dto.ModifyDeviceRequest;
import live.databo3.sensor.device.dto.RegisterDeviceRequest;

import java.util.List;

public interface DeviceService {
    DeviceResponse registerDevice(Integer organizationId, RegisterDeviceRequest request);
    DeviceResponse modifyDevice(Integer organizationId, String deviceSn, ModifyDeviceRequest request);
    List<DeviceDto> getDevices(Integer organizationId);
    DeviceResponse getDevice(Integer organizationId, String deviceSn);
    void deleteDevice(Integer organizationId, String deviceSn);
}
