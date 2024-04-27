package live.databo3.sensor.exception.already_exist_exception;

public class SensorAlreadyExistException extends AlreadyExistException {
    public SensorAlreadyExistException(String sensorSn) {
        super("sensor", "sensorSn-" + sensorSn);
    }
}
