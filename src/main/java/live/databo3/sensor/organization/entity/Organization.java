package live.databo3.sensor.organization.entity;

import live.databo3.sensor.organization.dto.RegisterOrganizationResponse;
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

    public RegisterOrganizationResponse toRegisterResponse() {
        return RegisterOrganizationResponse.builder()
                .organizationId(organizationId)
                .organizationName(organizationName)
                .build();
    }
}
