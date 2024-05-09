package live.databo3.sensor.member.adaptor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "account-service")
public interface MemberAdaptor {
    @GetMapping("/api/account/organizations/{organizationId}/members/{memberId}")
    boolean isAuthorizedAccess(@PathVariable("memberId") String memberId, @PathVariable("organizationId") Integer organizationId);
}
