package live.databo3.sensor.exception;

/**
 * 특정 조직에 대한 권한이 없는 유저의 요청을 받았을 때 던질 에러
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
public class UnAuthorizedAccessException extends RuntimeException {
    public UnAuthorizedAccessException(String message) {
        super(message);
    }
}
