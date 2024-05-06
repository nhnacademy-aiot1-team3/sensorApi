package live.databo3.sensor.sensor_type_mappings.entity;

import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor_type.entity.SensorType;
import live.databo3.sensor.sensor_type_mappings.dto.SensorTypeMappingResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sensor_type_mappings")
public class SensorTypeMappings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_number")
    private Long recordNumber;

    @ManyToOne
    @JoinColumn(name = "sensor_sn")
    private Sensor sensor;

    @ManyToOne
    @JoinColumn(name = "sensor_type_id")
    private SensorType sensorType;

    public SensorTypeMappingResponse toDto() {
        return SensorTypeMappingResponse.builder()
                .recordNumber(recordNumber)
                .sensorSn(sensor.getSensorSn())
                .sensorTypeId(sensorType.getSensorTypeId())
                .build();
    }
}
