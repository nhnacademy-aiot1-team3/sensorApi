package live.databo3.sensor.exception.already_exist_exception;

public class AlreadyExistException extends RuntimeException {
    public static final String MESSAGE = " Already Exists: ";

    public AlreadyExistException(String type, String message) {
        super(type + MESSAGE + message);
    }
}
