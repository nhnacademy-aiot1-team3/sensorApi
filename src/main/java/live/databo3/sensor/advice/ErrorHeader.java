package live.databo3.sensor.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorHeader {
    private int resultCode;
    private String resultMessage;
}
