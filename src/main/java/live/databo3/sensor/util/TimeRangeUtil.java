package live.databo3.sensor.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TimeRangeUtil {

    private TimeRangeUtil() {}

    public static Instant now() {
        return Instant.now();
    }

    public static Instant pastFiveMinutes() {
        return Instant.now().minus(5L,ChronoUnit.MINUTES);
    }

    public static Instant pastOneHour() {
        return Instant.now().minus(1L, ChronoUnit.HOURS);
    }

    public static Instant pastTwoHours() {
        return Instant.now().minus(2L, ChronoUnit.HOURS);
    }

    public static Instant pastOneDay() {
        return Instant.now().minus(1L, ChronoUnit.DAYS);
    }

    public static Instant pastTwoDays() {
        return Instant.now().minus(2L, ChronoUnit.DAYS);
    }

    public static Instant pastOneWeek() {
        return Instant.now().minus(7L,ChronoUnit.DAYS);
    }

    public static Instant pastTwoWeek() {
        return Instant.now().minus(14L, ChronoUnit.DAYS);
    }
}
