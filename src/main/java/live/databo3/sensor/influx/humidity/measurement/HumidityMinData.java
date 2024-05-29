package live.databo3.sensor.influx.humidity.measurement;

import com.influxdb.annotations.Column;
import lombok.Getter;

import java.time.Instant;

@Getter
public class HumidityMinData {

    @Column(name = "_time")
    private Instant time;

    @Column(name = "humidity_min")
    private Double value;
}
