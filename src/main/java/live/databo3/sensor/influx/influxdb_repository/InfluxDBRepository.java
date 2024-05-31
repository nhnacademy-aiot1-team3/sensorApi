package live.databo3.sensor.influx.influxdb_repository;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.query.dsl.Flux;
import live.databo3.sensor.properties.InfluxProperties;
import live.databo3.sensor.util.FluxQueryUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class InfluxDBRepository {

    private final InfluxDBClient influxDBClient;
    private final InfluxProperties influxProperties;

    //last
    public <T> Optional<T> findLastData(String field, String branch, String place, String endpoint, String sensor, Class<T> clazz) {
        Flux query = FluxQueryUtil.lastDataQuery(
                influxProperties.getSensorBucket(),
                field,
                branch,
                place,
                endpoint,
                sensor
        );

        return influxDBClient
                .getQueryApi()
                .query(query.toString(), clazz)
                .stream()
                .findFirst();
    }

    //hourly
    public <T> List<T> findFiveMinutesMeanDataForOneHour(Instant begin, Instant end, String field, String branch, String place, String endpoint, String sensor, Class<T> clazz) {
        Flux query = FluxQueryUtil.aggregationDataListQuery(
                begin,
                end,
                influxProperties.getHourlyMeanBucket(),
                field,
                branch,
                place,
                endpoint,
                sensor
        );

        return influxDBClient.getQueryApi().query(query.toString(), clazz);
    }

    //daily 현 시간 기준 오늘 최근 24시간, 현 시간 기준 어제 최근 24시간
    public <T> List<T> findOneHourMeanDataForOneDay(Instant begin, Instant end, String field, String branch, String place, String endpoint, String sensor, Class<T> clazz) {
        Flux query = FluxQueryUtil.aggregationDataListQuery(
                begin,
                end,
                influxProperties.getDailyMeanBucket(),
                field,
                branch,
                place,
                endpoint,
                sensor
        );

        return influxDBClient.getQueryApi().query(query.toString(), clazz);
    }

    //weekly min max
    public <T> List<T> findOneDayMinMaxDataForOneWeek(Instant begin, Instant end, String field, String branch, String place, String endpoint, String sensor, Class<T> clazz) {
        Flux query = FluxQueryUtil.aggregationDataListQuery(
                begin,
                end,
                influxProperties.getWeeklyMinMaxBucket(),
                field,
                branch,
                place,
                endpoint,
                sensor
        );

        return influxDBClient.getQueryApi().query(query.toString(), clazz);
    }

    public <T> List<T> findOneDayMeanDataForOneWeek(Instant begin, Instant end, String field, String branch, String place, String endpoint, String sensor, Class<T> clazz) {
        Flux query = FluxQueryUtil.aggregationDataListQuery(
                begin,
                end,
                influxProperties.getWeeklyMeanBucket(),
                field,
                branch,
                place,
                endpoint,
                sensor
        );

        return influxDBClient.getQueryApi().query(query.toString(), clazz);
    }

}
