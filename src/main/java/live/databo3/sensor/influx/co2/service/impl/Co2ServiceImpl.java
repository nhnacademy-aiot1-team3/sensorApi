package live.databo3.sensor.influx.co2.service.impl;

import live.databo3.sensor.influx.co2.measurement.Co2LastData;
import live.databo3.sensor.influx.co2.measurement.Co2MeanData;
import live.databo3.sensor.influx.co2.service.Co2Service;
import live.databo3.sensor.influx.dto.ResponseListData;
import live.databo3.sensor.influx.dto.ResponseSingleData;
import live.databo3.sensor.influx.influxdb_repository.InfluxDBRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class Co2ServiceImpl implements Co2Service {

    private final InfluxDBRepository influxDBRepository;


    @Override
    public ResponseSingleData<Co2LastData> retrieveCo2LastData(
            String field, String branch, String place, String endpoint, String sensor) {

        Co2LastData lastData = influxDBRepository.findLastData(field, branch, place, endpoint, sensor, Co2LastData.class).orElse(null);

        ResponseSingleData<Co2LastData> singleData = new ResponseSingleData<>();
        singleData.setData(lastData);

        return singleData;
    }

    @Override
    public ResponseListData<Co2MeanData> retrieveCo2HourlyMeanData(
            String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end) {

        List<Co2MeanData> dataList = influxDBRepository.findFiveMinutesMeanDataForOneHour(begin, end, field, branch, place, endpoint, sensor, Co2MeanData.class);

        ResponseListData<Co2MeanData> responseListData = new ResponseListData<>();
        responseListData.setData(dataList);

        return responseListData;
    }

    @Override
    public ResponseListData<Co2MeanData> retrieveCo2DailyMeanData(
            String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end) {

        List<Co2MeanData> dataList = influxDBRepository.findOneHourMeanDataForOneDay(begin, end, field, branch, place, endpoint, sensor, Co2MeanData.class);

        ResponseListData<Co2MeanData> responseListData = new ResponseListData<>();
        responseListData.setData(dataList);

        return responseListData;
    }

    @Override
    public ResponseListData<Co2MeanData> retrieveCo2WeeklyMean(
            String field, String branch, String place, String endpoint, String sensor, Instant begin, Instant end) {

        List<Co2MeanData> dataList = influxDBRepository.findOneDayMeanDataForOneWeek(begin, end, field, branch, place, endpoint, sensor, Co2MeanData.class);

        ResponseListData<Co2MeanData> responseListData = new ResponseListData<>();
        responseListData.setData(dataList);

        return responseListData;
    }
}
