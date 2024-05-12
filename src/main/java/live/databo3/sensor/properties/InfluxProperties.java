package live.databo3.sensor.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("influxdb")
public class InfluxProperties {

    private String url;
    private String org;
    private String token;

}
