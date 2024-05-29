package live.databo3.sensor.util;

import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class FluxQueryUtil {

    private FluxQueryUtil() {}

    private static final String ROW_KEY = "_time";
    private static final String COLUMN_KEY = "_field";
    private static final String COLUMN_VALUE = "_value";

    //실시간 마지막 값 가져오기
    public static Flux lastDataQuery(String bucket, String field, String branch, String place, String endpoint, String sensor) {

        Restrictions restrictions = RestrictionUtil.getRestriction(field, branch, place, endpoint, sensor);

        return Flux.from(bucket)
                .range(TimeRangeUtil.pastOneHour())
                .filter(restrictions)
                .last()
                .pivot()
                .withRowKey(new String[]{ROW_KEY})
                .withColumnKey(new String[]{COLUMN_KEY})
                .withValueColumn(COLUMN_VALUE)
                .timeShift(+9L, ChronoUnit.HOURS);
    }

    //집계 버킷들에서 특정 시간 만큼 데이터 들고오는 공통 쿼리
    public static Flux aggregationDataListQuery(Instant start, Instant stop, String bucket, String field, String branch, String place, String endPoint, String sensor) {

        Restrictions restrictions = RestrictionUtil.getRestriction(field, branch, place, endPoint, sensor);

        return Flux.from(bucket)
                .range(start, stop)
                .filter(restrictions)
                .pivot()
                .withRowKey(new String[]{ROW_KEY})
                .withColumnKey(new String[]{COLUMN_KEY})
                .withValueColumn(COLUMN_VALUE)
                .timeShift(+9L, ChronoUnit.HOURS);
    }

    /**
     * 모든 장소에 대한 값을 들고오는 query 작성
     * @param bucket influxdb bucket
     * @param field influxdb의 field 이름
     * @param branch influxdb의 branch 이름
     * @param endpoint influxdb의 endpoint 이름
     * @return query문 반환
     * @since 1.0.1
     */
    public static Flux lastDataForEveryWhereQuery(String bucket, String field, String branch, String endpoint) {

        Restrictions restrictions = RestrictionUtil.getRestrictionForEveryPlace(field, branch, endpoint);

        return Flux.from(bucket)
                .range(TimeRangeUtil.pastOneHour())
                .filter(restrictions)
                .last()
                .pivot()
                .withRowKey(new String[]{ROW_KEY})
                .withColumnKey(new String[]{COLUMN_KEY})
                .withValueColumn(COLUMN_VALUE)
                .timeShift(+9L, ChronoUnit.HOURS);
    }

}
