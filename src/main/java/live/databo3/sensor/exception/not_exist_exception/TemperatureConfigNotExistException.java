package live.databo3.sensor.exception.not_exist_exception;

public class TemperatureConfigNotExistException extends NotExistException {
    public TemperatureConfigNotExistException(Long configId) {
        super("temperatureConfig", "configId-" + configId);
    }
}
