package live.databo3.sensor.aop;

import live.databo3.sensor.exception.IllegalRefreshRedisUsageException;
import live.databo3.sensor.exception.UnAuthorizedAccessException;
import live.databo3.sensor.member.adaptor.MemberAdaptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CheckPermissionAspect {
    private final MemberAdaptor memberAdaptor;

    @Pointcut("@annotation(live.databo3.sensor.annotations.CheckPermission)")
    public void needCheckPermission() {}

    @Around("needCheckPermission()")
    public Object checkPermission(ProceedingJoinPoint pjp) throws Throwable {
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

        if (!Objects.nonNull(organizationId)) {
            throw new IllegalRefreshRedisUsageException("no organizationId");
        }
        checkPermissionWithOrganizationId(organizationId);

        return pjp.proceed();
    }

    public void checkPermissionWithOrganizationId(Integer organizationId) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            String userId = request.getHeader("X-USER-ID");
            log.debug("permission checking: userId: " + userId + ", organizationId: " + organizationId);
            if (userId != null) {
                if (Boolean.FALSE.equals(memberAdaptor.isAuthorizedAccess(userId, organizationId).getBody())) {
                    throw new UnAuthorizedAccessException("허가되지 않은 접근입니다.");
                }
            } else {
                throw new UnAuthorizedAccessException("로그인이 되지 않았습니다.");
            }
        }
    }
}
