package live.databo3.sensor.exception.already_exist_exception;

public class TemperatureConfigAlreadyExistException extends AlreadyExistException {
    public TemperatureConfigAlreadyExistException(Long configId) {
        super("temperatureConfig", "configId-" + configId);
    }
}
