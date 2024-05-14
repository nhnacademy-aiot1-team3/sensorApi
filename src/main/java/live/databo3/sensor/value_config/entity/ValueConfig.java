package live.databo3.sensor.value_config.entity;

import live.databo3.sensor.general_config.entity.GeneralConfig;
import live.databo3.sensor.value_config.dto.ValueConfigResponse;
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
@Table(name = "value_configs")
public class ValueConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "value_config_number")
    private Long valueConfigNumber;

    @Column(name = "first_entry")
    private String firstEntry;

    @Column(name = "second_entry")
    private String secondEntry;

    @ManyToOne
    @JoinColumn(name = "record_number")
    private GeneralConfig generalConfig;

    public ValueConfigResponse toDto() {
        return ValueConfigResponse.builder()
                .recordNumber(generalConfig.getRecordNumber())
                .valueConfigNumber(valueConfigNumber)
                .firstEntry(firstEntry)
                .secondEntry(secondEntry)
                .build();
    }
}
