package live.databo3.sensor.aop;

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

/**
 * ruleEngine 에서 참고할 사용자 설정과 관련된 db가 수정 될 때 db와 공유하는
 * redis 의 특정 키 값에 해당하는 내용을 비우는 역할을 한다.
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Slf4j
@Aspect
@Component
public class ClearRedisAspect {
    /**
     * redis 입력을 위한 빈
     * @since 1.0.0
     */
    private final RedisTemplate<String, Object> redisTemplate;
    /**
     * 조직의 이름을 조회하기 위한 서비스
     * @since 1.0.0
     */
    private final OrganizationService organizationService;

    public ClearRedisAspect(RedisTemplate<String, Object> redisTemplate, OrganizationService organizationService) {
        this.redisTemplate = redisTemplate;
        this.organizationService = organizationService;
    }

    /**
     * 메소드용 어노테이션 ClearRedis 에 해당하는 포인트 컷
     * @since 1.0.0
     */
    @Pointcut("@annotation(live.databo3.sensor.annotations.ClearRedis)")
    public void databaseChangeOperation() {}

    /**
     * ClearRedis 에 해당하는 어노테이션이 타켓 메서드의 Integer organization 을 추출하여
     * 조직의 이름을 조회 한다.
     * 메소드가 정상적으로 실행 된 후 redis 키값이 조직의 이름에 해당하는 데이터를 비운다.
     *
     * @since 1.0.0
     */
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
            } else if (parameters[i].getName().equals("organizationName") && paramValues[i] instanceof String) {
                organizationId = organizationService.findIdByName((String) paramValues[i]);
                break;
            }
        }
        Object retVal = pjp.proceed();

        if (!Objects.nonNull(organizationId)) {
            throw new NullPointerException("no organizationId");
        }
        String organizationName = organizationService.findNameById(organizationId);
        redisTemplate.delete(organizationName);

        log.debug("redis cleared, key: " + organizationName);
        return retVal;
    }
}
