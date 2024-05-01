package live.databo3.sensor.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ErrorResponse<T, S> {
    private T header;
    private S body;
}
