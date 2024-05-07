package live.databo3.sensor.listener;

import live.databo3.sensor.annotations.RefreshRedis;
import live.databo3.sensor.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

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
