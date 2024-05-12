package live.databo3.sensor.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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
