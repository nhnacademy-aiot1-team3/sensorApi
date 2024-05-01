package live.databo3.sensor.aop;

import live.databo3.sensor.exception.not_exist_exception.GeneralConfigNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorNotExistException;
import live.databo3.sensor.general_config.dto.request.GeneralConfigUpdateRequest;
import live.databo3.sensor.general_config.dto.request.modify.ModifyGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.request.register.RegisterGeneralConfigRequest;
import live.databo3.sensor.general_config.entity.GeneralConfig;
import live.databo3.sensor.general_config.repository.GeneralConfigRepository;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor.repository.SensorRepository;
import live.databo3.sensor.sensor_config.dto.request.SensorConfigRequest;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PermissionCheckAspect {
    private final GeneralConfigRepository generalConfigRepository;
    private final SensorRepository sensorRepository;

    // configId를 포함하는 json Post 요청으로 sensor 의 설정을 변경하는 요청
    @Before("CommonPointcuts.sensorConfigAccessOperation() && " +
            "(CommonPointcuts.registerConfigOperation() || CommonPointcuts.modifyConfigOperation())")
    public void checkPermissionForSensorConfigRegisterOrModify(JoinPoint joinPoint) {
        Object request = joinPoint.getArgs()[0];
        if (!(request instanceof SensorConfigRequest)) {
            throw new IllegalArgumentException("request not instanceof sensorConfigRequest");
        }
        Long configId = ((SensorConfigRequest)request).getConfigId();
        GeneralConfig generalConfig = generalConfigRepository.findById((configId)).orElseThrow(() -> new GeneralConfigNotExistException(configId));
        generalConfig.getSensor().getOrganization().getOrganizationId();
    }

    // configId 로 요청하는 설정 변경 요청
    @Before("(CommonPointcuts.sensorConfigAccessOperation() || CommonPointcuts.generalConfigAccessOperation()) && " +
            "(CommonPointcuts.deleteConfigOperation() || CommonPointcuts.getConfigWithConfigId())")
    public void checkPermissionForConfigGetOrDelete(JoinPoint joinPoint) {
        Object request = joinPoint.getArgs()[0];
        if (!(request instanceof Long)) {
            throw new IllegalArgumentException("request not instanceof Long");
        }
        Long configId = (Long) request;
        GeneralConfig generalConfig = generalConfigRepository.findById((configId)).orElseThrow(() -> new GeneralConfigNotExistException(configId));
        generalConfig.getSensor().getOrganization().getOrganizationId();
    }

    // sensorSn 으로 요청하는 일반 설정 변경 요청
    @Before("CommonPointcuts.generalConfigAccessOperation() && CommonPointcuts.registerConfigOperation()")
    public void checkPermissionForGeneralConfigRegister(JoinPoint joinPoint) {
        Object request = joinPoint.getArgs()[0];
        if (!(request instanceof RegisterGeneralConfigRequest)) {
            throw new IllegalArgumentException("request not instanceof RegisterGeneralConfigRequest");
        }
        String sensorSn = String.valueOf(((RegisterGeneralConfigRequest)request).getSensorSn());
        Sensor sensor = sensorRepository.findById(sensorSn).orElseThrow(() -> new SensorNotExistException(sensorSn));
        sensor.getOrganization().getOrganizationId();
    }


    @Before("CommonPointcuts.generalConfigAccessOperation() && CommonPointcuts.modifyConfigOperation()")
    public void checkPermissionForGeneralConfigModify(JoinPoint joinPoint) {
        Object request = joinPoint.getArgs()[0];
        if (!(request instanceof ModifyGeneralConfigRequest)) {
            throw new IllegalArgumentException("request not instanceof ModifyGeneralConfig");
        }
        Long configId = ((ModifyGeneralConfigRequest)request).getConfigId();
        GeneralConfig generalConfig = generalConfigRepository.findById((configId)).orElseThrow(() -> new GeneralConfigNotExistException(configId));
        generalConfig.getSensor().getOrganization().getOrganizationId();
    }



}
