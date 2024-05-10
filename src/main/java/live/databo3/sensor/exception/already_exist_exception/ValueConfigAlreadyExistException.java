package live.databo3.sensor.exception.already_exist_exception;

/**
 * ValueConfig 가 이미 존재할 때 던질 에러
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
public class ValueConfigAlreadyExistException extends AlreadyExistException {
    public ValueConfigAlreadyExistException(String sensorSn, Integer sensorTypeId) {
        super("valueConfig", "sensorSn-" + sensorSn + ", sensorTypeId-" + sensorTypeId);
    }
}
