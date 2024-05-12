package live.databo3.sensor.aop;

import live.databo3.sensor.exception.UnAuthorizedAccessException;
import live.databo3.sensor.member.adaptor.MemberAdaptor;
import live.databo3.sensor.util.ExtractHeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 * argument 중 Integer type 의 organizationId 라는 이름을 가진 파라미터를 가져와 메소드 실행 전에
 * account-api 에 해당 조직에 대한 권한이 있는 사용자인지 체크하는 보안 목적의 aspect
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CheckPermissionAspect {
    /**
     * account-api 에 FeignClient 요청을 하기위한 어댑터
     * @since 1.0.0
     */
    private final MemberAdaptor memberAdaptor;
    private final ExtractHeaderUtil extractHeaderUtil;

    /**
     * 메소드용 어노테이션 CheckPermission 에 해당하는 포인트 컷
     * @since 1.0.0
     */
    @Pointcut("@annotation(live.databo3.sensor.annotations.CheckPermission)")
    public void needCheckPermission() {}

    /**
     * CheckPermission 포인트 컷의 메소드 실행 전에 arg 의 Integer organizationId 를
     * 추출하여 checkPermissionWithOrganizationId()에 전달한다.
     * @since 1.0.0
     */
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
            throw new NullPointerException("no organizationId");
        }
        checkPermissionWithOrganizationId(organizationId);

        return pjp.proceed();
    }

    /**
     * 로그인한 사용자가 적합할 경우 gateway 에서 추가해주는 X-USER-ID 헤더에서 추출한 유저 아이디와 organizationId 를
     * 이용하여 account-api 에 권한 검사 요청을 한다.
     * @since 1.0.0
     */
    public void checkPermissionWithOrganizationId(Integer organizationId) {
        String userId = extractHeaderUtil.extractHeader("X-USER-ID");
        log.debug("permission checking: userId: " + userId + ", organizationId: " + organizationId);
        if (userId != null) {
            if (Boolean.FALSE.equals(memberAdaptor.isAuthorizedAccess(userId, organizationId).getBody())) {
                throw new UnAuthorizedAccessException("허가되지 않은 접근입니다.");
            }
        } else {
            throw new UnAuthorizedAccessException("로그인이 되지 않았습니다.");
        }
        log.debug("permission check: ok: userId-" + userId);
    }
}
