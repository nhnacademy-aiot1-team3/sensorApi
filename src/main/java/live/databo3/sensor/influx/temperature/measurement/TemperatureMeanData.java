package live.databo3.sensor.influx.temperature.measurement;

import com.influxdb.annotations.Column;
import lombok.Getter;

import java.time.Instant;

@Getter
public class TemperatureMeanData {

    @Column(name = "_time")
    private Instant time;

    @Column(name = "temperature_mean")
    private Double value;

}
