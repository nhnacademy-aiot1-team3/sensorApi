package live.databo3.sensor.aop;

import live.databo3.sensor.exception.IllegalRefreshRedisUsageException;
import live.databo3.sensor.general_config.service.GeneralConfigService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

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

    public void refreshRedisWithOrganizationId(Integer organizationId) {

    }
}
