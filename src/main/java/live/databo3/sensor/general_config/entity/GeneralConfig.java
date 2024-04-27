package live.databo3.sensor.general_config.entity;

import live.databo3.sensor.general_config.dto.response.GeneralConfigResponse;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.settingFunctionType.entity.SettingFunctionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "general_configs")
public class GeneralConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "config_id")
    private Long configId;

    @OneToOne
    @JoinColumn(name = "sensor_sn")
    private Sensor sensor;

    @ManyToOne
    @JoinColumn(name = "function_id")
    private SettingFunctionType settingFunctionType;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    public GeneralConfigResponse toDto() {
        return GeneralConfigResponse.builder()
                .configId(configId)
                .sensorSn(sensor.getSensorSn())
                .functionId(settingFunctionType.getFunctionId())
                .lastUpdateDate(lastUpdateDate)
                .build();
    }
}
