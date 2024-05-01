package live.databo3.sensor.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorHeader {
    private int resultCode;
    private String resultMessage;
}
