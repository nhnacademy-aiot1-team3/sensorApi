package live.databo3.sensor.exception.already_exist_exception;

public class HumidityConfigAlreadyExistException extends AlreadyExistException {
    public HumidityConfigAlreadyExistException(Long configId) {
        super("humidityConfig", "configId-" + configId);
    }
}
