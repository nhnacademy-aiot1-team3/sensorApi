package live.databo3.sensor.influx.humidity.measurement;

import com.influxdb.annotations.Column;
import lombok.Getter;

import java.time.Instant;

@Getter
public class HumidityMeanData {
    @Column(name = "_time")
    private Instant time;

    @Column(name = "humidity_mean")
    private Double value;
}
