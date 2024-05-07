package live.databo3.sensor.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse<T, S> {
    private T header;
    private S body;
}
