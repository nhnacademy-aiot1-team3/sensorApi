package live.databo3.sensor.exception.not_exist_exception;

/**
 * device 가 존재 하지 않을 때 던질 에러
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
public class DeviceNotExistException extends NotExistException {
    public DeviceNotExistException(String deviceSn) {
        super("device", "device-sn: " + deviceSn);
    }
}
