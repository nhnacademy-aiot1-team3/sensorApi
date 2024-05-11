package live.databo3.sensor.exception.not_exist_exception;

public class PlaceNotExistException extends NotExistException {
    public PlaceNotExistException(Integer placeId) {
        super("place", "place_id: " + placeId);
    }
}
