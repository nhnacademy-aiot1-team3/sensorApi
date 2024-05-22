package live.databo3.sensor.influx.humidity.measurement;

import com.influxdb.annotations.Column;
import lombok.Getter;

import java.time.Instant;

@Getter
public class HumidityLastData {

    @Column(name = "_time")
    private Instant time;

    @Column(name = "value")
    private Double value;

}
