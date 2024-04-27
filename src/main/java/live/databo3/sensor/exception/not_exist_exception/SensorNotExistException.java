package live.databo3.sensor.exception.not_exist_exception;

public class SensorNotExistException extends NotExistException {
    public SensorNotExistException(String sensorSn) {
        super("sensor", "sensorSn-" + sensorSn);
    }
}
