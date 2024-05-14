package live.databo3.sensor.device.repository;

import live.databo3.sensor.device.dto.DeviceDto;
import live.databo3.sensor.device.entity.Device;
import live.databo3.sensor.organization.entity.Organization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class DeviceRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    DeviceRepository deviceRepository;

    Organization organization;
    Device device;

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

        entityManager.persist(organization);
        entityManager.persist(device);
    }

    @Test
    void findByDeviceSnAndOrganization_OrganizationIdTest() {
        Optional<Device> foundDevice = deviceRepository.findByDeviceSnAndOrganization_OrganizationId(device.getDeviceSn(), organization.getOrganizationId());

        assertTrue(foundDevice.isPresent());
        assertEquals(foundDevice.get().getDeviceSn(), device.getDeviceSn());
        assertEquals(foundDevice.get().getDeviceName(), device.getDeviceName());
        assertEquals(foundDevice.get().getOrganization(), device.getOrganization());
    }

    @Test
    void findAllByOrganization_OrganizationIdTest() {
        List<DeviceDto> list = deviceRepository.findAllByOrganization_OrganizationId(organization.getOrganizationId());

        assertEquals(1, list.size());
        assertEquals(list.get(0).getDeviceSn(), device.getDeviceSn());
        assertEquals(list.get(0).getDeviceName(), device.getDeviceName());
    }

    @Test
    void existsByDeviceSnAndOrganization_OrganizationIdTest() {
        boolean isExist = deviceRepository.existsByDeviceSnAndOrganization_OrganizationId(device.getDeviceSn(), organization.getOrganizationId());

        assertTrue(isExist);
    }

    @Test
    void findByDeviceSnTest() {
        Optional<Device> foundDevice = deviceRepository.findByDeviceSn(device.getDeviceSn());

        assertTrue(foundDevice.isPresent());
        assertEquals(foundDevice.get().getDeviceName(), device.getDeviceName());
        assertEquals(foundDevice.get().getOrganization(), device.getOrganization());
    }
}
