package live.databo3.sensor.exception.not_exist_exception;

public class SensorTypeNotExistException extends NotExistException {
    public SensorTypeNotExistException(Integer sensorTypeId) {
        super("sensorType", "sensorTypeId-" + sensorTypeId);
    }
}
