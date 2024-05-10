package live.databo3.sensor.exception.already_exist_exception;

/**
 * GeneralConfig 가 이미 존재할 때 던질 에러
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
public class GeneralConfigAlreadyExistException extends AlreadyExistException {
    public GeneralConfigAlreadyExistException(String sensorSn, Integer sensorTypeId) {
        super("generalConfig", "sensorSn-" + sensorSn + ", sensorTypeId-" + sensorTypeId);
    }
}
