package live.databo3.sensor.exception.not_exist_exception;

/**
 * sensorTypeMapping 이 존재하지 않을 때 던질 에러
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
public class SensorTypeMappingNotExistException extends NotExistException {
    public SensorTypeMappingNotExistException(String sensorSn, Integer sensorTypeId) {
        super("sensorTypeMapping", "sensorSn-" + sensorSn + ", sensorTypeId-" + sensorTypeId);
    }
}
