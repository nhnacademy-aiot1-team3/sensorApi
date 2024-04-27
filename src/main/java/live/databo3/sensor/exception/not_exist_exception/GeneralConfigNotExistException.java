package live.databo3.sensor.exception.not_exist_exception;

public class GeneralConfigNotExistException extends NotExistException {
    public GeneralConfigNotExistException(Long configId) {
        super("generalConfig", "configId-" + configId);
    }
}
