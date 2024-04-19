package live.databo3.sensor.settingFunctionType.entity;

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
@Table(name = "setting_function_type")
public class SettingFunctionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "function_id")
    private Long functionId;

    @Column(name = "function_name")
    private String functionName;
}
