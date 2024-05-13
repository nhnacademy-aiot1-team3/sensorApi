package live.databo3.sensor.influx.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ResponseListData<T> {

    private List<T> data;

}
