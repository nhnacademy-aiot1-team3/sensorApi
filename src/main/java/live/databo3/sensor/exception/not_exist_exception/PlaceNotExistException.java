package live.databo3.sensor.exception.not_exist_exception;

/**
 * place 가 존재하지 않을 때 던질 에러
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
public class PlaceNotExistException extends NotExistException {
    public PlaceNotExistException(Integer placeId) {
        super("place", "place_id: " + placeId);
    }
}
