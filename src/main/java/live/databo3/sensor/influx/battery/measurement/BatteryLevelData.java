package live.databo3.sensor.influx.battery.measurement;

import com.influxdb.annotations.Column;
import lombok.Getter;

import java.time.Instant;

@Getter
public class BatteryLevelData {

    @Column(name = "_time")
    private Instant time;

    @Column(name = "place")
    private String place;

    @Column(name = "device")
    private String device;

    @Column(name = "branch")
    private String branch;

    @Column(name = "topic")
    private String topic;

    @Column(name = "value")
    private int value;
}
