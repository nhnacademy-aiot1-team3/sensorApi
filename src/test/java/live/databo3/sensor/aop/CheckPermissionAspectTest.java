package live.databo3.sensor.aop;

import live.databo3.sensor.annotations.CheckPermission;
import live.databo3.sensor.exception.UnAuthorizedAccessException;
import live.databo3.sensor.member.adaptor.MemberAdaptor;
import live.databo3.sensor.util.ExtractHeaderUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckPermissionAspectTest {
    @Mock
    private MemberAdaptor memberAdaptor;

    @Mock
    private ExtractHeaderUtil extractHeaderUtil;

    @InjectMocks
    private CheckPermissionAspect checkPermissionAspect;

    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;

    @Mock
    private MethodSignature methodSignature;

    @Test
    void testCheckPermission_ValidOrganizationId() throws Throwable {
        when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(CheckPermissionTestClass.class.getMethod("needCheckPermission", Integer.class));
        when(proceedingJoinPoint.getArgs()).thenReturn(new Object[]{1});

        when(extractHeaderUtil.extractHeader(anyString())).thenReturn("user123");
        when(memberAdaptor.isAuthorizedAccess(anyString(), anyInt())).thenReturn(ResponseEntity.ok(true));

        checkPermissionAspect.checkPermission(proceedingJoinPoint);
        verify(proceedingJoinPoint, times(1)).proceed();
    }

    @Test
    void testCheckPermission_InvalidOrganizationId() {
        when(extractHeaderUtil.extractHeader(anyString())).thenReturn("user123");
        when(memberAdaptor.isAuthorizedAccess(anyString(), anyInt())).thenReturn(ResponseEntity.ok(false));

        assertThrows(UnAuthorizedAccessException.class, () -> {
            checkPermissionAspect.checkPermissionWithOrganizationId(1);
        });
    }

    @Test
    void testCheckPermission_NoUserId() {
        when(extractHeaderUtil.extractHeader(anyString())).thenReturn(null);

        assertThrows(UnAuthorizedAccessException.class, () -> {
            checkPermissionAspect.checkPermissionWithOrganizationId(1);
        });
    }

    static class CheckPermissionTestClass {
        @CheckPermission
        public void needCheckPermission(Integer organizationId) {
        }
    }

}
