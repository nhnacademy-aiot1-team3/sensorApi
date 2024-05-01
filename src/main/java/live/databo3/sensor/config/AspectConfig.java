package live.databo3.sensor.config;

import live.databo3.sensor.aop.CommonPointcuts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {

    @Bean
    public CommonPointcuts commonPointcuts() {
        return new CommonPointcuts();
    }
}
