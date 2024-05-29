package live.databo3.sensor.influx.temperature.service;

import live.databo3.sensor.influx.dto.ResponseSingleData;
import live.databo3.sensor.influx.dto.ResponseListData;
import live.databo3.sensor.influx.temperature.measurement.TemperatureLastData;
import live.databo3.sensor.influx.temperature.measurement.TemperatureMaxData;
import live.databo3.sensor.influx.temperature.measurement.TemperatureMeanData;
import live.databo3.sensor.influx.temperature.measurement.TemperatureMinData;

import java.time.Instant;

public interface TemperatureService {

    ResponseSingleData<TemperatureLastData> retrieveTemperatureLastData(String field, String branch, String place, String endpoint, String sensor);

    ResponseListData<TemperatureMeanData> retrieveTemperatureHourlyMeanData(String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end);

    ResponseListData<TemperatureMeanData> retrieveTemperatureDailyMeanData(String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end);

    ResponseListData<TemperatureMinData> retrieveTemperatureWeeklyMinData(String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end);

    ResponseListData<TemperatureMaxData> retrieveTemperatureWeeklyMaxData(String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end);
}
