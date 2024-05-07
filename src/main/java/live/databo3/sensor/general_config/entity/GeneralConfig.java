package live.databo3.sensor.general_config.entity;

import live.databo3.sensor.general_config.dto.response.GeneralConfigResponse;
import live.databo3.sensor.sensor_type_mappings.entity.SensorTypeMappings;
import live.databo3.sensor.setting_function_type.entity.SettingFunctionType;
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
    @Column(name = "record_number")
    private Long recordNumber;

    @OneToOne
    @MapsId
    @JoinColumn(name = "record_number")
    private SensorTypeMappings sensorTypeMappings;

    @ManyToOne
    @JoinColumn(name = "function_id")
    private SettingFunctionType settingFunctionType;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    public GeneralConfigResponse toDto() {
        return GeneralConfigResponse.builder()
                .recordNumber(recordNumber)
                .functionId(settingFunctionType.getFunctionId())
                .lastUpdateDate(lastUpdateDate)
                .build();
    }
}
