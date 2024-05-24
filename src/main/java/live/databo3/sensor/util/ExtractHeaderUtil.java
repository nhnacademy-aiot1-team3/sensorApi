package live.databo3.sensor.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 특정 헤더의 값을 추출하기 위한 유틸 클래스
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Component
public class ExtractHeaderUtil {
    public String extractHeader(String header) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String value = "";
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            value = request.getHeader(header);
        }
        return value;
    }
}
