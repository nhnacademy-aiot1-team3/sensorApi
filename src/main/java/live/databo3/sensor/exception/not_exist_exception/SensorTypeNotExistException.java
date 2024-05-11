package live.databo3.sensor.exception.not_exist_exception;

/**
 * SensorType 이 존재하지 않을 때 던질 에러
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
public class SensorTypeNotExistException extends NotExistException {
    public SensorTypeNotExistException(Integer sensorTypeId) {
        super("sensorType", "sensorTypeId-" + sensorTypeId);
    }

    public SensorTypeNotExistException(String sensorType) {
        super("sensorType", "sensorType-" + sensorType);
    }
}
