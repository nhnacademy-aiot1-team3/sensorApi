package live.databo3.sensor.general_config.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "general_config")
public class GeneralConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "config_id")
    private Long configId;

    @Column(name = "sensor_sn")
    private String sensorSn;

    @Column(name = "function_id")
    private Long functionId;

    @Column(name = "last_update_date")
    private Date lastUpdateDate;

//    @OneToOne(mappedBy = "generalConfig")
//    private TemperatureConfig temperatureConfig;
}
