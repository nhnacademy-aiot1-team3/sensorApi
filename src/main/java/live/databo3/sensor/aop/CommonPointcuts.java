package live.databo3.sensor.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CommonPointcuts {
    @Pointcut("within(live.databo3.sensor.general_config.controller..*)")
    public void generalConfigAccessOperation() {}

    @Pointcut("within(live.databo3.sensor.sensor_config.controller..*)")
    public void sensorConfigAccessOperation() {}

    @Pointcut("execution(* create*(..))")
    public void registerConfigOperation() {}

    @Pointcut("execution(* modify*(..))")
    public void modifyConfigOperation() {}

    @Pointcut("execution(* delete*(..))")
    public void deleteConfigOperation() {}

    @Pointcut("execution(* get*Name(..))")
    public void getConfigWithOrganizationName() {}

    @Pointcut("execution(* get*Config(..))")
    public void getConfigWithConfigId() {}

}
