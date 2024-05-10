package live.databo3.sensor.listener;

import live.databo3.sensor.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * application 의 시작을 감지하는 listener
 * application 이 시작된 후 최초 1회 레디스의 모든 키에 해당하는 값들을 지웁니다.
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Component
@RequiredArgsConstructor
public class ApplicationStartListener implements ApplicationListener<ApplicationStartedEvent> {
    private final OrganizationService organizationService;
    private final RedisInitializer redisInitializer;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        List<Integer> organizationIdList = organizationService.findIdList();
        for (Integer organizationId : organizationIdList) {
            redisInitializer.redisInitialize(organizationId);
        }
    }

}
