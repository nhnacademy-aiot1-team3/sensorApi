package live.databo3.sensor.device_log.entity;

import live.databo3.sensor.device.entity.Device;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "device_logs")
public class DeviceLog {
    @Id
    @Column(name = "log_id")
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "device_sn")
    private Device device;

    @Column
    private LocalDateTime timestamp;

    @Column
    private Double value;
}
