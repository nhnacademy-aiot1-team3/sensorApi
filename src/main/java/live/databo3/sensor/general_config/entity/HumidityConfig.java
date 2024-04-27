package live.databo3.sensor.general_config.entity;

import live.databo3.sensor.general_config.dto.response.HumidityConfigResponse;
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
@Table(name = "humidity_configs")
public class HumidityConfig {
    @Id
    @Column(name = "config_id")
    private Long configId;

    @Column(name = "target_value")
    private double targetValue;

    @Column(name = "deviation_value")
    private double deviationValue;

    @OneToOne
    @MapsId
    @JoinColumn(name = "config_id")
    private GeneralConfig generalConfig;

    public HumidityConfigResponse toDto() {
        return HumidityConfigResponse.builder()
                .configId(configId)
                .targetValue(targetValue)
                .deviationValue(deviationValue)
                .build();
    }
}
