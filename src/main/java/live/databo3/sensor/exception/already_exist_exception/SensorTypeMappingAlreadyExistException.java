package live.databo3.sensor.exception.already_exist_exception;

/**
 * SensorType 이 이미 존재할 때 던질 에러
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
public class SensorTypeMappingAlreadyExistException extends AlreadyExistException {
    public SensorTypeMappingAlreadyExistException(String sensorSn, Integer sensorTypeId) {
        super("sensorTypeMapping", "sensorSn-" + sensorSn + ", sensorTypeId-" + sensorTypeId);
    }
}
