package live.databo3.sensor.influx.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseSingleData<T> {

    private T data;

}
