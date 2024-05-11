package live.databo3.sensor.device.repository;

import live.databo3.sensor.device.dto.DeviceDto;
import live.databo3.sensor.device.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, String> {
    Optional<Device> findByDeviceSnAndOrganization_OrganizationId(String deviceSn, Integer organizationId);
    List<DeviceDto> findAllByOrganization_OrganizationId(Integer organizationId);
    boolean existsByDeviceSnAndOrganization_OrganizationId(String deviceSn, Integer organizationId);
    Optional<Device> findByDeviceSn(String deviceSn);
}
