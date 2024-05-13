package live.databo3.sensor.influx.temperature.service.impl;

import live.databo3.sensor.influx.dto.ResponseListData;
import live.databo3.sensor.influx.dto.ResponseSingleData;
import live.databo3.sensor.influx.temperature.measurement.TemperatureLastData;
import live.databo3.sensor.influx.temperature.measurement.TemperatureMaxData;
import live.databo3.sensor.influx.temperature.measurement.TemperatureMeanData;
import live.databo3.sensor.influx.temperature.measurement.TemperatureMinData;
import live.databo3.sensor.influx.influxdb_repository.InfluxDBRepository;
import live.databo3.sensor.influx.temperature.service.TemperatureService;
import live.databo3.sensor.util.TimeRangeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemperatureServiceImpl implements TemperatureService {

    private final InfluxDBRepository temperatureRepository;

    @Override
    public ResponseSingleData<TemperatureLastData> retrieveLastData(String field, String branch, String place, String endpoint, String sensor) {

        TemperatureLastData lastData = temperatureRepository.findLastTemperatureData(field, branch, place, endpoint, sensor, TemperatureLastData.class).orElse(null);
        log.info("service last data : {}", lastData);

        ResponseSingleData<TemperatureLastData> responseSingleData = new ResponseSingleData<>();
        responseSingleData.setData(lastData);

        return responseSingleData;
    }

    @Override
    public ResponseListData<TemperatureMeanData> retrieveHourlyMeanData(String field, String branch, String place, String endpoint, String sensor) {

        List<TemperatureMeanData> dataList = temperatureRepository.findFiveMinutesMeanDataForOneHour(TimeRangeUtil.pastOneHour(), TimeRangeUtil.now(), field, branch, place, endpoint, sensor, TemperatureMeanData.class);
        log.info("service hour data : {}", dataList);

        ResponseListData<TemperatureMeanData> responseListData = new ResponseListData<>();
        responseListData.setData(dataList);

        return responseListData;
    }

    @Override
    public ResponseListData<TemperatureMeanData> retrieveDailyMeanData(String field, String branch, String place, String endpoint, String sensor) {

        List<TemperatureMeanData> dataList = temperatureRepository.findOneHourMeanDataForOneDay(TimeRangeUtil.pastTwoDays(), TimeRangeUtil.pastOneDay(), field, branch, place, endpoint, sensor, TemperatureMeanData.class);
        log.info("service daily data : {}", dataList);

        ResponseListData<TemperatureMeanData> singleListData = new ResponseListData<>();
        singleListData.setData(dataList);

        return singleListData;
    }

    @Override
    public ResponseListData<TemperatureMinData> retrieveWeeklyMinData(String field, String branch, String place, String endpoint, String sensor) {

        List<TemperatureMinData> dataList = temperatureRepository.findOneDayMinMaxDataForOneWeek(TimeRangeUtil.pastOneWeek(), TimeRangeUtil.now(), field, branch, place, endpoint, sensor, TemperatureMinData.class);
        log.info("service min max data : {}", dataList);

        ResponseListData<TemperatureMinData> minListData = new ResponseListData<>();
        minListData.setData(dataList);

        return minListData;
    }

    @Override
    public ResponseListData<TemperatureMaxData> retrieveWeeklyMaxData(String field, String branch, String place, String endpoint, String sensor) {
        List<TemperatureMaxData> dataList = temperatureRepository.findOneDayMinMaxDataForOneWeek(TimeRangeUtil.pastOneWeek(), TimeRangeUtil.now(), field, branch, place, endpoint, sensor, TemperatureMaxData.class);
        log.info("service min max data : {}", dataList);

        ResponseListData<TemperatureMaxData> maxListData = new ResponseListData<>();
        maxListData.setData(dataList);

        return maxListData;
    }
}
