package live.databo3.sensor.exception.not_exist_exception;

public class ValueConfigNotExistException extends NotExistException {
    public ValueConfigNotExistException(Long valueConfigNumber) {
        super("valueConfig", "valueConfigNumber-" + valueConfigNumber);
    }
}
