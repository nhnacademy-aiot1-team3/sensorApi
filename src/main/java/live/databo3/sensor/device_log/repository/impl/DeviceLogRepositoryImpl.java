package live.databo3.sensor.device_log.repository.impl;

import com.querydsl.core.types.Projections;
import live.databo3.sensor.device.entity.QDevice;
import live.databo3.sensor.device_log.dto.DeviceLogResponseDto;
import live.databo3.sensor.device_log.entity.DeviceLog;
import live.databo3.sensor.device_log.entity.QDeviceLog;
import live.databo3.sensor.device_log.repository.DeviceLogRepositoryCustom;
import live.databo3.sensor.organization.entity.QOrganization;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

public class DeviceLogRepositoryImpl extends QuerydslRepositorySupport implements DeviceLogRepositoryCustom {
    private final QDeviceLog deviceLog = QDeviceLog.deviceLog;
    private final QDevice device = QDevice.device;
    private final QOrganization organization = QOrganization.organization;

    public DeviceLogRepositoryImpl() {
        super(DeviceLog.class);
    }

    @Override
    public Optional<List<DeviceLogResponseDto>> getDeviceLogs(Integer organizationId) {
        return Optional.ofNullable(from(deviceLog)
                .select(Projections.constructor(DeviceLogResponseDto.class,
                        deviceLog.logId,
                        device,
                        deviceLog.timestamp,
                        deviceLog.value
                ))
                .leftJoin(device).on(deviceLog.device.eq(device))
                .leftJoin(organization).on(device.organization.eq(organization))
                .where(organization.organizationId.eq(organizationId))
                .fetch());
    }
}
