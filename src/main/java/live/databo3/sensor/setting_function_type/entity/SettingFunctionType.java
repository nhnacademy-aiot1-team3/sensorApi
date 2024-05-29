package live.databo3.sensor.setting_function_type.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * settingFunction entity
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "setting_function_types")
public class SettingFunctionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "function_id")
    private Long functionId;

    /**
     * settingFunction read_only/ai/custom 등의 설정을 관리하기 위한 column.
     *
     * @since 1.0.0
     */
    @Column(name = "function_name")
    @Enumerated(EnumType.STRING)
    private SETTINGFUNCTIONTYPE functionName;

    public enum SETTINGFUNCTIONTYPE {
        READ_ONLY,
        CUSTOM,
        AI
    }
}
