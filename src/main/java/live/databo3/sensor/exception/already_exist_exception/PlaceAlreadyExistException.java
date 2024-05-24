package live.databo3.sensor.exception.already_exist_exception;

/**
 * Place 가 이미 존재할 때 던질 에러
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
public class PlaceAlreadyExistException extends AlreadyExistException {
    public PlaceAlreadyExistException(String placeName) {
        super("place", "placeName-" + placeName);
    }
}
