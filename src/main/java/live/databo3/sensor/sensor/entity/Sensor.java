package live.databo3.sensor.sensor.entity;

import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.place.entity.Place;
import live.databo3.sensor.sensor.dto.SensorResponse;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "sensors")
public class Sensor {
    @Id
    @Column(name = "sensor_sn")
    private String sensorSn;

    @Column(name = "sensor_name")
    private String sensorName;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    public SensorResponse toDto() {
        return SensorResponse.builder()
                .sensorSn(sensorSn)
                .sensorName(sensorName)
                .placeId(place.getPlaceId())
                .placeName(place.getPlaceName())
                .build();
    }

}
