package live.databo3.sensor.exception.already_exist_exception;

public class GeneralConfigAlreadyExistException extends AlreadyExistException {
    public GeneralConfigAlreadyExistException(String sensorSn) {
        super("generalConfig", "sensorSn-" + sensorSn);
    }
}
