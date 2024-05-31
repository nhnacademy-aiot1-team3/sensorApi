package live.databo3.sensor.influx.humidity.service.impl;

import live.databo3.sensor.influx.dto.ResponseListData;
import live.databo3.sensor.influx.dto.ResponseSingleData;
import live.databo3.sensor.influx.humidity.measurement.HumidityLastData;
import live.databo3.sensor.influx.humidity.measurement.HumidityMaxData;
import live.databo3.sensor.influx.humidity.measurement.HumidityMeanData;
import live.databo3.sensor.influx.humidity.measurement.HumidityMinData;
import live.databo3.sensor.influx.humidity.service.HumidityService;
import live.databo3.sensor.influx.influxdb_repository.InfluxDBRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HumidityServiceImpl implements HumidityService {

    private final InfluxDBRepository influxDBRepository;

    @Override
    public ResponseSingleData<HumidityLastData> retrieveHumidityLastData(
            String field, String branch, String place, String endpoint, String sensor) {

        HumidityLastData lastData = influxDBRepository.findLastData(field, branch, place, endpoint, sensor, HumidityLastData.class).orElse(null);

        ResponseSingleData<HumidityLastData> singleData = new ResponseSingleData<>();
        singleData.setData(lastData);

        return singleData;
    }

    @Override
    public ResponseListData<HumidityMeanData> retrieveHumidityHourlyMeanData(
            String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end) {

        List<HumidityMeanData> dataList = influxDBRepository.findFiveMinutesMeanDataForOneHour(begin, end, field, branch, place, endpoint, sensor, HumidityMeanData.class);

        ResponseListData<HumidityMeanData> responseListData = new ResponseListData<>();
        responseListData.setData(dataList);

        return responseListData;
    }

    @Override
    public ResponseListData<HumidityMeanData> retrieveHumidityDailyMeanData(
            String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end) {

        List<HumidityMeanData> dataList = influxDBRepository.findOneHourMeanDataForOneDay(begin, end, field, branch, place, endpoint, sensor, HumidityMeanData.class);

        ResponseListData<HumidityMeanData> responseListData = new ResponseListData<>();
        responseListData.setData(dataList);

        return responseListData;
    }

    @Override
    public ResponseListData<HumidityMinData> retrieveHumidityWeeklyMinData(
            String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end) {

        List<HumidityMinData> dataList = influxDBRepository.findOneDayMinMaxDataForOneWeek(begin, end, field, branch, place, endpoint, sensor, HumidityMinData.class);

        ResponseListData<HumidityMinData> minListData = new ResponseListData<>();
        minListData.setData(dataList);

        return minListData;
    }

    @Override
    public ResponseListData<HumidityMaxData> retrieveHumidityWeeklyMaxData(
            String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end) {

        List<HumidityMaxData> dataList = influxDBRepository.findOneDayMinMaxDataForOneWeek(begin, end, field, branch, place, endpoint, sensor, HumidityMaxData.class);

        ResponseListData<HumidityMaxData> maxListData = new ResponseListData<>();
        maxListData.setData(dataList);

        return maxListData;
    }
}
