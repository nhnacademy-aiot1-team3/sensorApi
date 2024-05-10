package live.databo3.sensor.device.service.impl;

import live.databo3.sensor.device.dto.DeviceDto;
import live.databo3.sensor.device.dto.DeviceResponse;
import live.databo3.sensor.device.dto.ModifyDeviceRequest;
import live.databo3.sensor.device.dto.RegisterDeviceRequest;
import live.databo3.sensor.device.entity.Device;
import live.databo3.sensor.device.repository.DeviceRepository;
import live.databo3.sensor.device.service.DeviceService;
import live.databo3.sensor.exception.already_exist_exception.DeviceAlreadyExistException;
import live.databo3.sensor.exception.not_exist_exception.DeviceNotExistException;
import live.databo3.sensor.exception.not_exist_exception.OrganizationNotExistException;
import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.organization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final OrganizationRepository organizationRepository;

    public DeviceResponse registerDevice(Integer organizationId, RegisterDeviceRequest request) {
        String deviceSn = request.getDeviceSn();
        if (deviceRepository.existsById(deviceSn)) {
            throw new DeviceAlreadyExistException(deviceSn);
        }

        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new OrganizationNotExistException(organizationId));

        Device device = new Device(deviceSn, request.getDeviceName(), organization);

        return deviceRepository.save(device).toDto();
    }

    @Transactional
    public DeviceResponse modifyDevice(Integer organizationId, String deviceSn, ModifyDeviceRequest request) {
        Device device = deviceRepository.findByDeviceSnAndOrganization_OrganizationId(deviceSn, organizationId).orElseThrow(() -> new DeviceNotExistException(deviceSn));

        device.setDeviceName(request.getDeviceName());

        return device.toDto();
    }

    public List<DeviceDto> getDevices(Integer organizationId) {
        return deviceRepository.findAllByOrganization_OrganizationId(organizationId);
    }

    public DeviceResponse getDevice(Integer organizationId, String deviceSn) {
        return deviceRepository.findByDeviceSnAndOrganization_OrganizationId(deviceSn, organizationId).orElseThrow(() -> new DeviceNotExistException(deviceSn)).toDto();
    }

    public void deleteDevice(Integer organizationId, String deviceSn) {
        if (!deviceRepository.existsByDeviceSnAndOrganization_OrganizationId(deviceSn, organizationId)) {
            throw new DeviceNotExistException(deviceSn);
        }
        deviceRepository.deleteById(deviceSn);
    }
}
