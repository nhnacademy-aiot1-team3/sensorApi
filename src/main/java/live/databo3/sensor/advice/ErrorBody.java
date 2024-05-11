package live.databo3.sensor.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * errorResponse 에 담아줄 body
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Getter
@AllArgsConstructor
public class ErrorBody {
    private String message;
}
