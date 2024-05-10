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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * device entity 관련 service
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final OrganizationRepository organizationRepository;
    /**
     * 이미 존재하는 device 인지 확인 한 후에 없다면 request 의 body 를 통해 생성한다.
     * @since 1.0.0
     */
    public DeviceResponse registerDevice(Integer organizationId, RegisterDeviceRequest request) {
        String deviceSn = request.getDeviceSn();
        if (deviceRepository.existsById(deviceSn)) {
            throw new DeviceAlreadyExistException(deviceSn);
        }

        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new OrganizationNotExistException(organizationId));

        Device device = new Device(deviceSn, request.getDeviceName(), organization);

        deviceRepository.save(device);

        return device.toDto();
    }

    /**
     * device 를 조회하여 수정한다.
     * @since 1.0.0
     */
    @Transactional
    public DeviceResponse modifyDevice(Integer organizationId, String deviceSn, ModifyDeviceRequest request) {
        Device device = deviceRepository.findByDeviceSnAndOrganization_OrganizationId(deviceSn, organizationId).orElseThrow(() -> new DeviceNotExistException(deviceSn));

        device.setDeviceName(request.getDeviceName());

        return device.toDto();
    }

    /**
     * 특정 조직에 속하는 device 들의 list 를 반환한다.
     * @since 1.0.0
     */
    public List<DeviceDto> getDevices(Integer organizationId) {
        return deviceRepository.findAllByOrganization_OrganizationId(organizationId);
    }

    /**
     * device 정보 일부를 반환한다.
     * @since 1.0.0
     */
    public DeviceResponse getDevice(Integer organizationId, String deviceSn) {
        return deviceRepository.findByDeviceSnAndOrganization_OrganizationId(deviceSn, organizationId).orElseThrow(() -> new DeviceNotExistException(deviceSn)).toDto();
    }

    /**
     * device 가 존재하는지 체크한 후 존재한다면 제거한다.
     * @since 1.0.0
     */
    public void deleteDevice(Integer organizationId, String deviceSn) {
        if (!deviceRepository.existsByDeviceSnAndOrganization_OrganizationId(deviceSn, organizationId)) {
            throw new DeviceNotExistException(deviceSn);
        }
        deviceRepository.deleteById(deviceSn);
    }
}
