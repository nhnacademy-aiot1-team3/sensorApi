package live.databo3.sensor.exception.not_exist_exception;

public class NotExistException extends RuntimeException{
    public static final String MESSAGE = " Not Exists: ";

    public NotExistException(String type, String message) {
        super(type + MESSAGE + message);
    }
}
