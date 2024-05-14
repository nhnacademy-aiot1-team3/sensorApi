package live.databo3.sensor.exception.already_exist_exception;

/**
 * deviceSn이 중복될 경우 던질 에러
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
public class DeviceAlreadyExistException extends AlreadyExistException {
    public DeviceAlreadyExistException(String deviceSn) {
        super("device", "device-sn: " + deviceSn);
    }
}
