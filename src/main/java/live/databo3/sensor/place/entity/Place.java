package live.databo3.sensor.place.entity;

import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.place.dto.PlaceResponse;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "places")
public class Place {
    @Id
    @Column(name = "place_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer placeId;

    @Column(name = "place_name")
    private String placeName;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    public PlaceResponse toDto() {
        return PlaceResponse.builder()
                .placeId(placeId)
                .placeName(placeName)
                .build();
    }
}
