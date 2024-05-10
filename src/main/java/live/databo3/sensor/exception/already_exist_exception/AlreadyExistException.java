package live.databo3.sensor.exception.already_exist_exception;

/**
 * 유일성을 보장해야하는 리소스에 대한 추가요청이 일어났을 시에 던질 에러
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
public class AlreadyExistException extends RuntimeException {
    public static final String MESSAGE = " Already Exists: ";

    public AlreadyExistException(String type, String message) {
        super(type + MESSAGE + message);
    }
}
