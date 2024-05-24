package live.databo3.sensor.member.adaptor;

import live.databo3.sensor.member.dto.MemberOrganizationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * openFeign 기능을 통해 account-api 에 요청을 합니다.
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@FeignClient(value = "account-service")
public interface MemberAdaptor {
    /**
     * 조직 id와 유저의 id를 이용하여 account-api 에 해당 유저가 해당 조직에 속하는지, 그리고 해당 조직에 승인되어있는지의 여부를 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/api/account/organizations/{organizationId}/members/{memberId}")
    ResponseEntity<Boolean> isAuthorizedAccess(@PathVariable("memberId") String memberId, @PathVariable("organizationId") Integer organizationId);

    /**
     * 유저가 속한 조직의 목록들을 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/api/account/organizations/members/{memberId}")
    ResponseEntity<List<MemberOrganizationDto>> getOrganizationsByMember(@PathVariable("memberId") String memberId);

}


