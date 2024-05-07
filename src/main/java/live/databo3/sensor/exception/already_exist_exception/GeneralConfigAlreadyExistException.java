package live.databo3.sensor.exception.already_exist_exception;

public class GeneralConfigAlreadyExistException extends AlreadyExistException {
    public GeneralConfigAlreadyExistException(String sensorSn, Integer sensorTypeId) {
        super("generalConfig", "sensorSn-" + sensorSn + ", sensorTypeId-" + sensorTypeId);
    }
}
