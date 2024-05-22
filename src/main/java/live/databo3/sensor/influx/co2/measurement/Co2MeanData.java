package live.databo3.sensor.influx.co2.measurement;

import com.influxdb.annotations.Column;
import lombok.Getter;

import java.time.Instant;

@Getter
public class Co2MeanData {

    @Column(name = "_time")
    private Instant time;

    @Column(name = "co2_mean")
    private Double value;

}
