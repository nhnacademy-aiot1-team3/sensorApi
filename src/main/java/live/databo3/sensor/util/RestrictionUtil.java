package live.databo3.sensor.util;

import com.influxdb.query.dsl.functions.restriction.Restrictions;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestrictionUtil {

    private RestrictionUtil() {}

    private static final String MEASUREMENT = "sensor";
    private static final String BRANCH = "branch";
    private static final String PLACE = "place";
    private static final String DEVICE = "device";
    private static final String ENDPOINT = "endpoint";

    public static Restrictions getRestriction(String field, String branch, String place, String endPoint, String sensor) {

        Restrictions restrictions = Restrictions
                .and(
                        Restrictions.measurement().equal(MEASUREMENT),
                        Restrictions.field().equal(field),
                        Restrictions.tag(BRANCH).equal(branch),
                        Restrictions.tag(PLACE).equal(place),
                        Restrictions.tag(DEVICE).equal(sensor),
                        Restrictions.tag(ENDPOINT).equal(endPoint)
                );

        log.info("res : {}", restrictions);

        return restrictions;
    }

}
