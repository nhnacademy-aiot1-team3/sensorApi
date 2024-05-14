package live.databo3.sensor.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * error 발생시 responseEntity 에 담아줄 객체
 * errorResponse
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Getter
@AllArgsConstructor
public class ErrorResponse<T, S> {
    private T header;
    private S body;
}
