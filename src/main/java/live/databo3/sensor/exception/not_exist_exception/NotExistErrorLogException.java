package live.databo3.sensor.exception.not_exist_exception;

public class NotExistErrorLogException extends NotExistException{
    private static final String Message = "ErrorLog 가 존재하지 않습니다.";

    public NotExistErrorLogException() {
        super("ErrorLog",Message);
    }
}
