package live.databo3.sensor.exception.already_exist_exception;

public class PlaceAlreadyExistException extends AlreadyExistException {
    public PlaceAlreadyExistException(String placeName) {
        super("place", "placeName-" + placeName);
    }
}
