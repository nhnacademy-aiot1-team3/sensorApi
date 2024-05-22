package live.databo3.sensor.influx.humidity.service;

import live.databo3.sensor.influx.dto.ResponseListData;
import live.databo3.sensor.influx.dto.ResponseSingleData;
import live.databo3.sensor.influx.humidity.measurement.HumidityLastData;
import live.databo3.sensor.influx.humidity.measurement.HumidityMaxData;
import live.databo3.sensor.influx.humidity.measurement.HumidityMeanData;
import live.databo3.sensor.influx.humidity.measurement.HumidityMinData;

import java.time.Instant;

public interface HumidityService {
    ResponseSingleData<HumidityLastData> retrieveHumidityLastData(String field, String branch, String place, String endpoint, String sensor);

    ResponseListData<HumidityMeanData> retrieveHumidityHourlyMeanData(String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end);

    ResponseListData<HumidityMeanData> retrieveHumidityDailyMeanData(String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end);

    ResponseListData<HumidityMinData> retrieveHumidityWeeklyMinData(String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end);

    ResponseListData<HumidityMaxData> retrieveHumidityWeeklyMaxData(String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end);
}
