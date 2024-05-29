package live.databo3.sensor.influx.temperature.measurement;

import com.influxdb.annotations.Column;
import lombok.Getter;

import java.time.Instant;

@Getter
public class TemperatureMinData {

    @Column(name = "_time")
    private Instant time;

    @Column(name = "temperature_min")
    private Double value;

}
