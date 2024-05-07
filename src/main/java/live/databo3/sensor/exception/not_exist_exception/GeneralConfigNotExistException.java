package live.databo3.sensor.exception.not_exist_exception;

public class GeneralConfigNotExistException extends NotExistException {
    public GeneralConfigNotExistException(String sensorSn, Integer sensorTypeId) {
        super("generalConfig", "sensorSn-" + sensorSn + ", sensorTypeId-" + sensorTypeId);
    }
}
