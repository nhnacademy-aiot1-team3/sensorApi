package live.databo3.sensor.aop;

import live.databo3.sensor.annotations.ClearRedis;
import live.databo3.sensor.organization.service.OrganizationService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClearRedisAspectTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private OrganizationService organizationService;

    @InjectMocks
    private ClearRedisAspect clearRedisAspect;

    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;

    @Mock
    private MethodSignature methodSignature;

    @Test
    void testClearRedis_OrganizationId() throws Throwable {
        when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(ClearRedisTestClass.class.getMethod("clearWithOrganizationId", Integer.class));
        when(proceedingJoinPoint.getArgs()).thenReturn(new Object[]{1});
        when(organizationService.findNameById(1)).thenReturn("OrganizationName");

        clearRedisAspect.clearRedis(proceedingJoinPoint);

        verify(redisTemplate, times(1)).delete(anyString());
    }

    @Test
    void testClearRedis_OrganizationName() throws Throwable {
        when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getMethod()).thenReturn(ClearRedisTestClass.class.getMethod("clearWithOrganizationName", String.class));
        when(proceedingJoinPoint.getArgs()).thenReturn(new Object[]{"OrganizationName"});
        when(organizationService.findIdByName(anyString())).thenReturn(1);
        when(organizationService.findNameById(anyInt())).thenReturn("OrganizationName");

        clearRedisAspect.clearRedis(proceedingJoinPoint);

        verify(redisTemplate, times(1)).delete(anyString());
    }

    static class ClearRedisTestClass {
        @ClearRedis
        public void clearWithOrganizationId(Integer organizationId) {
        }

        @ClearRedis
        public void clearWithOrganizationName(String organizationName) {
        }
    }
}