package live.databo3.sensor.sensor.entity;

import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.sensor.dto.RegisterSensorResponse;
import live.databo3.sensor.sensorType.entity.SensorType;
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
@Table(name = "sensor")
public class Sensor {
    @Id
    @Column(name = "sensor_sn")
    private String sensorSn;

    @Column(name = "sensor_name")
    private String sensorName;

    @Column(name = "sensor_place")
    private String sensorPlace;

    @ManyToOne
    @JoinColumn(name = "organizationId")
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "sensorTypeId")
    private SensorType sensorType;

    public RegisterSensorResponse toDto() {
        return RegisterSensorResponse.builder()
                .sensorSn(sensorSn)
                .sensorName(sensorName)
                .sensorPlace(sensorPlace)
                .organizationId(organization.getOrganizationId())
                .sensorTypeId(sensorType.getSensorTypeId())
                .build();
    }

}
