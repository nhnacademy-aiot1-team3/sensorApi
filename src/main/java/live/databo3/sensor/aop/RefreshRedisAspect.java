package live.databo3.sensor.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import live.databo3.sensor.exception.IllegalRefreshRedisUsageException;
import live.databo3.sensor.general_config.dto.GeneralConfigDto;
import live.databo3.sensor.general_config.dto.GeneralConfigForRedis;
import live.databo3.sensor.general_config.service.GeneralConfigService;
import live.databo3.sensor.organization.service.OrganizationService;
import live.databo3.sensor.value_config.dto.ValueConfigDto;
import live.databo3.sensor.value_config.dto.ValueConfigForRedisDto;
import live.databo3.sensor.value_config.service.ValueConfigService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Objects;

@Aspect
@Component
public class RefreshRedisAspect {
    private final RedisTemplate<String, Object> redisTemplate;
    private final GeneralConfigService generalConfigService;
    private final ValueConfigService valueConfigService;
    private final OrganizationService organizationService;
    private final ObjectMapper objectMapper;

    public RefreshRedisAspect(RedisTemplate<String, Object> redisTemplate, GeneralConfigService generalConfigService, ValueConfigService valueConfigService, OrganizationService organizationService, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.generalConfigService = generalConfigService;
        this.valueConfigService = valueConfigService;
        this.organizationService = organizationService;
        this.objectMapper = objectMapper;
    }

    @Pointcut("@annotation(live.databo3.sensor.annotations.RefreshRedis)")
    public void databaseChangeOperation() {}

    @Around("databaseChangeOperation()")
    public Object refreshRedis(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] paramValues = pjp.getArgs();
        Integer organizationId = null;

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getName().equals("organizationId") && paramValues[i] instanceof Integer) {
                organizationId = (Integer) paramValues[i];
                break;
            }
        }
        Object retVal = pjp.proceed();

        if (!Objects.nonNull(organizationId)) {
            throw new IllegalRefreshRedisUsageException("no organizationId");
        }
        refreshRedisWithOrganizationId(organizationId);

        return retVal;
    }

    public void refreshRedisWithOrganizationId(Integer organizationId) throws JsonProcessingException {
        String organizationName = organizationService.findNameById(organizationId);
        redisTemplate.delete(organizationName);

        List<GeneralConfigDto> generalConfigDtoList = generalConfigService.findGeneralConfigByOrganizationId(organizationId);
        List<ValueConfigDto> valueConfigDtoList = valueConfigService.getValueConfigListByOrganizationId(organizationId);

        for (GeneralConfigDto dto : generalConfigDtoList) {
            redisTemplate.opsForHash().put(organizationName, "sn/" + dto.getSensorSn() + "/type/" + dto.getSensorType() + "/general" , objectMapper.writeValueAsString(new GeneralConfigForRedis(dto.getFunctionName())));
        }
        for (ValueConfigDto dto : valueConfigDtoList) {
            redisTemplate.opsForHash().put(organizationName,"sn/" + dto.getSensorSn() + "/type/" + dto.getSensorType() + "/value", objectMapper.writeValueAsString(new ValueConfigForRedisDto(dto.getFirstEntry(), dto.getSecondEntry())));
        }
    }
}
