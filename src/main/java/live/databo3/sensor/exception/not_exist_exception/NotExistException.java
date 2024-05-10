package live.databo3.sensor.exception.not_exist_exception;

/**
 * 존재하지 않는 리소스에 대한 조회/수정/삭제 요청이 일어났을 때 던질 에러
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
public class NotExistException extends RuntimeException{
    public static final String MESSAGE = " Not Exists: ";

    public NotExistException(String type, String message) {
        super(type + MESSAGE + message);
    }
}
