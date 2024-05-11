package live.databo3.sensor.member.adaptor;

import live.databo3.sensor.member.dto.MemberOrganizationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * openFeign 기능을 통해 account-api 에 특정 유저가 특정 회사에 대한 권한이 있는지 여부를 확인하는 요청을 합니다.
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@FeignClient(value = "account-service")
public interface MemberAdaptor {
    @GetMapping("/api/account/organizations/{organizationId}/members/{memberId}")
    ResponseEntity<Boolean> isAuthorizedAccess(@PathVariable("memberId") String memberId, @PathVariable("organizationId") Integer organizationId);

    @GetMapping("/api/account/organizations/members/{memberId}")
    ResponseEntity<List<MemberOrganizationDto>> getOrganizationsByMember(@PathVariable("memberId") String memberId);

}


