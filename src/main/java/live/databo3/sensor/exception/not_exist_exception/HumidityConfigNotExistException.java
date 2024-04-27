package live.databo3.sensor.exception.not_exist_exception;

public class HumidityConfigNotExistException extends NotExistException {
    public HumidityConfigNotExistException(Long configId) {
        super("humidityConfig", "configId" + configId);
    }
}
