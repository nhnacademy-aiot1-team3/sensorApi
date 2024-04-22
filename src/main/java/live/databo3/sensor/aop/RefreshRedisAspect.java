package live.databo3.sensor.aop;

import live.databo3.sensor.general_config.dto.*;
import live.databo3.sensor.general_config.entity.GeneralConfig;
import live.databo3.sensor.general_config.entity.HumidityConfig;
import live.databo3.sensor.general_config.entity.TemperatureConfig;
import live.databo3.sensor.general_config.service.GeneralConfigService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class RefreshRedisAspect {
    private final RedisTemplate<String, Object> redisTemplate;
    private final GeneralConfigService generalConfigService;

    @Pointcut("@annotation(live.databo3.sensor.annotations.RefreshRedis)")
    public void databaseChangeOperation() {}

    @Around("databaseChangeOperation()")
    public Object refreshRedis(ProceedingJoinPoint pjp) throws Throwable {
        Object request = pjp.getArgs()[0];
        String organizationName = "";
        Object retVal = null;

        if (request instanceof SensorConfigRequest) {
            organizationName = generalConfigService.getOrganizationNameByConfigId(((SensorConfigRequest)request).getConfigId());
            retVal = pjp.proceed();
        } else if (request instanceof RegisterGeneralConfigRequest) {
            retVal = pjp.proceed();
            if (retVal instanceof RegisterGeneralConfigResponse) {
                Long configId = ((RegisterGeneralConfigResponse) retVal).getConfigId();
                organizationName = generalConfigService.getOrganizationNameByConfigId(configId);
            }
        } else if (request instanceof GeneralConfigRUDRequest) {
            organizationName = generalConfigService.getOrganizationNameByConfigId(((GeneralConfigRUDRequest)request).getConfigId());
            retVal = pjp.proceed();
        } else {
            // todo dirty exception
            throw new RuntimeException("invalid request");
        }

        refreshRedisWithOrganizationName(organizationName);


        return retVal;
    }

    public void refreshRedisWithOrganizationName(String organizationName) {
        List<GeneralConfig> generalConfigList = generalConfigService.findGeneralConfigByOrganizationName(organizationName);
        List<TemperatureConfig> tempList = generalConfigService.findTemperatureConfigByOrganizationName(organizationName);
        List<HumidityConfig> humList = generalConfigService.findHumidityConfigByOrganizationName(organizationName);

        redisTemplate.delete(organizationName);

        for (GeneralConfig generalConfig : generalConfigList) {
            String generalHashKey = "sn:" + generalConfig.getSensor().getSensorSn() +
                    "/funcType";

            redisTemplate.opsForHash().put(organizationName, generalHashKey, generalConfig.getSettingFunctionType().getFunctionName());
        }

        for (TemperatureConfig temperatureConfig : tempList) {
            String tempHashKeyPrefix = "/type:temp/sn:" + temperatureConfig.getGeneralConfig().getSensor().getSensorSn();

            redisTemplate.opsForHash().put(organizationName, tempHashKeyPrefix + "/target", temperatureConfig.getTargetValue());
            redisTemplate.opsForHash().put(organizationName, tempHashKeyPrefix + "/deviation", temperatureConfig.getDeviationValue());
        }

        for (HumidityConfig humidityConfig : humList) {
            String humHashKeyPrefix = "/type:hum/sn:" + humidityConfig.getGeneralConfig().getSensor().getSensorSn();

            redisTemplate.opsForHash().put(organizationName, humHashKeyPrefix + "/target", humidityConfig.getTargetValue());
            redisTemplate.opsForHash().put(organizationName, humHashKeyPrefix + "/deviation", humidityConfig.getDeviationValue());
        }
    }
}
