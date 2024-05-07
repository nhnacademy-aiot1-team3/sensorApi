package live.databo3.sensor.member.adaptor;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "account-service")
public interface MemberAdaptor {

}
