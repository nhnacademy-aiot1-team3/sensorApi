package live.databo3.sensor.influx.temperature.service;

import live.databo3.sensor.influx.dto.ResponseSingleData;
import live.databo3.sensor.influx.dto.ResponseListData;
import live.databo3.sensor.influx.temperature.measurement.TemperatureLastData;
import live.databo3.sensor.influx.temperature.measurement.TemperatureMaxData;
import live.databo3.sensor.influx.temperature.measurement.TemperatureMeanData;
import live.databo3.sensor.influx.temperature.measurement.TemperatureMinData;

public interface TemperatureService {

    ResponseSingleData<TemperatureLastData> retrieveLastData(String field, String branch, String place, String endpoint, String sensor);

    ResponseListData<TemperatureMeanData> retrieveHourlyMeanData(String field, String branch, String place, String endpoint, String sensor);

    ResponseListData<TemperatureMeanData> retrieveDailyMeanData(String field, String branch, String place, String endpoint, String sensor);

    ResponseListData<TemperatureMinData> retrieveWeeklyMinData(String field, String branch, String place, String endpoint, String sensor);

    ResponseListData<TemperatureMaxData> retrieveWeeklyMaxData(String field, String branch, String place, String endpoint, String sensor);
}
