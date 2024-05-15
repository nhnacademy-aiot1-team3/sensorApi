package live.databo3.sensor.influx.temperature.service.impl;

import live.databo3.sensor.influx.dto.ResponseListData;
import live.databo3.sensor.influx.dto.ResponseSingleData;
import live.databo3.sensor.influx.temperature.measurement.TemperatureLastData;
import live.databo3.sensor.influx.temperature.measurement.TemperatureMaxData;
import live.databo3.sensor.influx.temperature.measurement.TemperatureMeanData;
import live.databo3.sensor.influx.temperature.measurement.TemperatureMinData;
import live.databo3.sensor.influx.influxdb_repository.InfluxDBRepository;
import live.databo3.sensor.influx.temperature.service.TemperatureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemperatureServiceImpl implements TemperatureService {

    private final InfluxDBRepository influxDBRepository;

    @Override
    public ResponseSingleData<TemperatureLastData> retrieveTemperatureLastData(
            String field, String branch, String place, String endpoint, String sensor) {

        TemperatureLastData lastData = influxDBRepository.findLastData(field, branch, place, endpoint, sensor, TemperatureLastData.class).orElse(null);
        log.info("service last data : {}", lastData);

        ResponseSingleData<TemperatureLastData> singleData = new ResponseSingleData<>();
        singleData.setData(lastData);

        return singleData;
    }

    @Override
    public ResponseListData<TemperatureMeanData> retrieveTemperatureHourlyMeanData(
            String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end) {

        List<TemperatureMeanData> dataList = influxDBRepository.findFiveMinutesMeanDataForOneHour(begin, end, field, branch, place, endpoint, sensor, TemperatureMeanData.class);
        log.info("service hour data : {}", dataList);

        ResponseListData<TemperatureMeanData> responseListData = new ResponseListData<>();
        responseListData.setData(dataList);

        return responseListData;
    }

    @Override
    public ResponseListData<TemperatureMeanData> retrieveTemperatureDailyMeanData(
            String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end) {

        List<TemperatureMeanData> dataList = influxDBRepository.findOneHourMeanDataForOneDay(begin, end, field, branch, place, endpoint, sensor, TemperatureMeanData.class);
        log.info("service daily data : {}", dataList);

        ResponseListData<TemperatureMeanData> responseListData = new ResponseListData<>();
        responseListData.setData(dataList);

        return responseListData;
    }

    @Override
    public ResponseListData<TemperatureMinData> retrieveTemperatureWeeklyMinData(
            String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end) {

        List<TemperatureMinData> dataList = influxDBRepository.findOneDayMinMaxDataForOneWeek(begin, end, field, branch, place, endpoint, sensor, TemperatureMinData.class);
        log.info("service min max data : {}", dataList);

        ResponseListData<TemperatureMinData> minListData = new ResponseListData<>();
        minListData.setData(dataList);

        return minListData;
    }

    @Override
    public ResponseListData<TemperatureMaxData> retrieveTemperatureWeeklyMaxData(
            String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end) {
        List<TemperatureMaxData> dataList = influxDBRepository.findOneDayMinMaxDataForOneWeek(begin, end, field, branch, place, endpoint, sensor, TemperatureMaxData.class);
        log.info("service min max data : {}", dataList);

        ResponseListData<TemperatureMaxData> maxListData = new ResponseListData<>();
        maxListData.setData(dataList);

        return maxListData;
    }
}
