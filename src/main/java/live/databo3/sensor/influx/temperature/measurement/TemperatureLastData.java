package live.databo3.sensor.influx.temperature.measurement;

import com.influxdb.annotations.Column;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Getter
@ToString
public class TemperatureLastData {

    @Column(name= "_time")
    private Instant time;

    @Column(name= "value")
    private Double value;

}
