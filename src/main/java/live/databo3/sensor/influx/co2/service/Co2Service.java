package live.databo3.sensor.influx.co2.service;

import live.databo3.sensor.influx.co2.measurement.Co2LastData;
import live.databo3.sensor.influx.co2.measurement.Co2MeanData;
import live.databo3.sensor.influx.dto.ResponseListData;
import live.databo3.sensor.influx.dto.ResponseSingleData;

import java.time.Instant;

public interface Co2Service {

    ResponseSingleData<Co2LastData> retrieveCo2LastData(String field, String branch, String place, String endpoint, String sensor);

    ResponseListData<Co2MeanData> retrieveCo2HourlyMeanData(String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end);

    ResponseListData<Co2MeanData> retrieveCo2DailyMeanData(String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end);

    ResponseListData<Co2MeanData> retrieveCo2WeeklyMean(String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end);

}
