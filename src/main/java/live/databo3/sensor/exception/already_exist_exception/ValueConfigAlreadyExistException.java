package live.databo3.sensor.exception.already_exist_exception;

public class ValueConfigAlreadyExistException extends AlreadyExistException {
    public ValueConfigAlreadyExistException(String sensorSn, Integer sensorTypeId) {
        super("valueConfig", "sensorSn-" + sensorSn + ", sensorTypeId-" + sensorTypeId);
    }
}
