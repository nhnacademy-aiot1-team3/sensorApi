package live.databo3.sensor.error_log.entity;

import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor_type.entity.SensorType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "error_logs")
public class ErrorLog {
    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "sensor_sn")
    private Sensor sensor;

    @ManyToOne
    @JoinColumn(name = "sensor_type_id")
    private SensorType sensorType;

    @Column
    private LocalDateTime timestamp;

    @Column
    private Double value;

    @Column(name = "error_msg")
    private String errorMsg;

}
