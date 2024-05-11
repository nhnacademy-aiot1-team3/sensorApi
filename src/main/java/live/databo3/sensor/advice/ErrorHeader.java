package live.databo3.sensor.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * errorResponse 에 담아줄 header
 * 내부에서 지정한 상태 코드를 포함합니다.
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Getter
@AllArgsConstructor
public class ErrorHeader {
    private int resultCode;
    private String resultMessage;
}
