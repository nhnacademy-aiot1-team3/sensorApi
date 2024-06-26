package live.databo3.sensor.device.entity;

import live.databo3.sensor.device.dto.DeviceResponse;
import live.databo3.sensor.organization.entity.Organization;
import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "devices")
public class Device {
    @Id
    @Column(name = "device_sn")
    private String deviceSn;

    @Column(name = "device_name")
    private String deviceName;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    public DeviceResponse toDto() {
        return DeviceResponse.builder()
                .deviceSn(deviceSn)
                .deviceName(deviceName)
                .build();
    }
}
