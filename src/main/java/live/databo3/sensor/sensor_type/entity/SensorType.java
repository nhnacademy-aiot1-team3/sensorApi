package live.databo3.sensor.sensor_type.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "sensor_types")
public class SensorType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_type_id")
    private Integer sensorTypeId;

    @Column(name = "sensor_type")
    private String sensorType;
}