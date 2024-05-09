package live.databo3.sensor.aop;

import live.databo3.sensor.exception.IllegalRefreshRedisUsageException;
import live.databo3.sensor.organization.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Aspect
@Component
public class ClearRedisAspect {
    private final RedisTemplate<String, Object> redisTemplate;
    private final OrganizationService organizationService;

    public ClearRedisAspect(RedisTemplate<String, Object> redisTemplate, OrganizationService organizationService) {
        this.redisTemplate = redisTemplate;
        this.organizationService = organizationService;
    }

    @Pointcut("@annotation(live.databo3.sensor.annotations.ClearRedis)")
    public void databaseChangeOperation() {}

    @Around("databaseChangeOperation()")
    public Object clearRedis(ProceedingJoinPoint pjp) throws Throwable {
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
        String organizationName = organizationService.findNameById(organizationId);
        redisTemplate.delete(organizationName);

        log.debug("redis cleared, key: " + organizationName);
        return retVal;
    }
}
