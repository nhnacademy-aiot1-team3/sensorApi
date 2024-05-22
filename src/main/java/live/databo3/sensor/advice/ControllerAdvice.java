package live.databo3.sensor.advice;

import live.databo3.sensor.exception.UnAuthorizedAccessException;
import live.databo3.sensor.exception.already_exist_exception.AlreadyExistException;
import live.databo3.sensor.exception.not_exist_exception.NotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * restController 에서 발생한 에러를 처리해 주는 advice
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class ControllerAdvice {
    /**
     * 유일성을 보장해야하는 리소스에 대한 생성 요청을 보내면 400을 보냅니다.
     * @since 1.0.0
     */
    @ExceptionHandler({AlreadyExistException.class})
    protected ResponseEntity<ErrorResponse<ErrorHeader, ErrorBody>> alReadyExistExceptionHandler(Exception e) {
        log.error(e.getMessage());
        ErrorHeader errorHeader = new ErrorHeader(101, "이미 존재합니다.");

        ErrorResponse<ErrorHeader, ErrorBody> errorResponse = new ErrorResponse<>(errorHeader, new ErrorBody(null));

        return ResponseEntity.badRequest().body(errorResponse);
    }
    /**
     * 존재하지 않는 리소스에 대한 조회/수정/삭제 요청을 보내면 400을 보냅니다.
     * @since 1.0.0
     */
    @ExceptionHandler({NotExistException.class})
    protected ResponseEntity<ErrorResponse<ErrorHeader, ErrorBody>> notExistExceptionHandler(Exception e) {
        log.error(e.getMessage());
        ErrorHeader errorHeader = new ErrorHeader(102, "존재하지 않습니다.");

        ErrorResponse<ErrorHeader, ErrorBody> errorResponse = new ErrorResponse<>(errorHeader, new ErrorBody(null));

        return ResponseEntity.badRequest().body(errorResponse);
    }
    /**
     * account-api 에 요청하여 받은 결과가 허가 되지 않은 접근이라면 401을 보냅니다.
     * @since 1.0.0
     */
    @ExceptionHandler({UnAuthorizedAccessException.class})
    protected ResponseEntity<ErrorResponse<ErrorHeader, ErrorBody>> unAuthorizedAccessExceptionHandler(Exception e) {
        log.error(e.getMessage());
        ErrorHeader errorHeader = new ErrorHeader(103, "허가되지 않은 접근입니다.");

        ErrorResponse<ErrorHeader, ErrorBody> errorResponse = new ErrorResponse<>(errorHeader, new ErrorBody(null));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
