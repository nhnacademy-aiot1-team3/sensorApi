package live.databo3.sensor.organization.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organization_id")
    private Integer organizationId;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "gateway_sn")
    private String gatewaySn;

    @Column(name = "controller_sn")
    private String controllerSn;
}
