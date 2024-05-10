package live.databo3.sensor.exception.not_exist_exception;

/**
 * GeneralConfig 가 이미 존재할 때 던질 에러
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
public class GeneralConfigNotExistException extends NotExistException {
    public GeneralConfigNotExistException(String sensorSn, Integer sensorTypeId) {
        super("generalConfig", "sensorSn-" + sensorSn + ", sensorTypeId-" + sensorTypeId);
    }
}
