package live.databo3.sensor.device.service;

import live.databo3.sensor.device.dto.DeviceResponse;
import live.databo3.sensor.device.dto.ModifyDeviceRequest;
import live.databo3.sensor.device.dto.RegisterDeviceRequest;
import live.databo3.sensor.device.entity.Device;
import live.databo3.sensor.device.repository.DeviceRepository;
import live.databo3.sensor.device.service.impl.DeviceServiceImpl;
import live.databo3.sensor.exception.already_exist_exception.DeviceAlreadyExistException;
import live.databo3.sensor.exception.not_exist_exception.DeviceNotExistException;
import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.organization.repository.OrganizationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {
    @Mock
    private DeviceRepository deviceRepository;
    @Mock
    private OrganizationRepository organizationRepository;
    @InjectMocks
    private DeviceServiceImpl deviceService;

    Organization organization;
    Device device;
    DeviceResponse deviceResponse;
    RegisterDeviceRequest registerDeviceRequest;
    ModifyDeviceRequest modifyDeviceRequest;

    @BeforeEach
    void setup() {
        organization = Organization.builder()
                .controllerSn("testControllerSn")
                .gatewaySn("testGatewaySn")
                .organizationName("testOrganizationName")
                .build();

        device = Device.builder()
                .deviceSn("testDeviceSn")
                .deviceName("testDeviceName")
                .organization(organization)
                .build();

        deviceResponse = device.toDto();

        registerDeviceRequest = new RegisterDeviceRequest();
        ReflectionTestUtils.setField(registerDeviceRequest, "deviceSn", "testDeviceSn");
        ReflectionTestUtils.setField(registerDeviceRequest, "deviceName", "testDeviceName");

        modifyDeviceRequest = new ModifyDeviceRequest();
        ReflectionTestUtils.setField(registerDeviceRequest, "deviceName", "testDeviceName");
    }

    @Test
    void deviceCreateSuccessTest() {
        when(deviceRepository.existsById(anyString())).thenReturn(false);
        when(organizationRepository.findById(anyInt())).thenReturn(Optional.of(organization));
        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        DeviceResponse response = deviceService.registerDevice(1, registerDeviceRequest);
        assertEquals(response.getDeviceSn(), deviceResponse.getDeviceSn());
        assertEquals(response.getDeviceName(), deviceResponse.getDeviceName());

        verify(deviceRepository, times(1)).existsById(anyString());
        verify(organizationRepository, times(1)).findById(anyInt());
        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    @Test
    void deviceCreateFailTest() {
        when(deviceRepository.existsById(anyString())).thenReturn(true);

        assertThatThrownBy(() -> deviceService.registerDevice(1, registerDeviceRequest))
                .isInstanceOf(DeviceAlreadyExistException.class)
                .hasMessageContaining(DeviceAlreadyExistException.MESSAGE);
    }

    @Test
    void deviceModifySuccessTest() {
        when(deviceRepository.findByDeviceSnAndOrganization_OrganizationId(anyString(), anyInt())).thenReturn(Optional.of(device));

        DeviceResponse response = deviceService.modifyDevice(1, device.getDeviceSn() , modifyDeviceRequest);
        assertEquals(response.getDeviceSn(), device.getDeviceSn());
        assertEquals(response.getDeviceName(), modifyDeviceRequest.getDeviceName());

        verify(deviceRepository, times(1)).findByDeviceSnAndOrganization_OrganizationId(anyString(), anyInt());
    }

    @Test
    void deviceModifyFailTest() {
        when(deviceRepository.findByDeviceSnAndOrganization_OrganizationId(anyString(), anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> deviceService.modifyDevice(1, device.getDeviceSn(), modifyDeviceRequest))
                .isInstanceOf(DeviceNotExistException.class)
                .hasMessageContaining(DeviceNotExistException.MESSAGE);
    }

    @Test
    void deviceGetSuccessTest() {
        when(deviceRepository.findByDeviceSnAndOrganization_OrganizationId(anyString(), anyInt())).thenReturn(Optional.of(device));

        DeviceResponse response = deviceService.getDevice(1, device.getDeviceSn());
        assertEquals(response.getDeviceSn(), device.getDeviceSn());
        assertEquals(response.getDeviceName(), device.getDeviceName());

        verify(deviceRepository, times(1)).findByDeviceSnAndOrganization_OrganizationId(anyString(), anyInt());
    }

    @Test
    void deviceGetFailTest() {
        when(deviceRepository.findByDeviceSnAndOrganization_OrganizationId(anyString(), anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> deviceService.getDevice(1, device.getDeviceSn()))
                .isInstanceOf(DeviceNotExistException.class)
                .hasMessageContaining(DeviceNotExistException.MESSAGE);
    }

    @Test
    void deviceDeleteSuccessTest() {
        when(deviceRepository.existsByDeviceSnAndOrganization_OrganizationId(anyString(), anyInt())).thenReturn(true);

        deviceService.deleteDevice(1, device.getDeviceSn());

        verify(deviceRepository, times(1)).existsByDeviceSnAndOrganization_OrganizationId(anyString(), anyInt());
    }

    @Test
    void deviceDeleteFailTest() {
        when(deviceRepository.existsByDeviceSnAndOrganization_OrganizationId(anyString(), anyInt())).thenReturn(false);

        assertThatThrownBy(() -> deviceService.deleteDevice(1, device.getDeviceSn()))
                .isInstanceOf(DeviceNotExistException.class)
                .hasMessageContaining(DeviceNotExistException.MESSAGE);
    }

    @Test
    void devicesGetTest() {
        deviceService.getDevices(1);

        verify(deviceRepository, times(1)).findAllByOrganization_OrganizationId(anyInt());
    }
}
