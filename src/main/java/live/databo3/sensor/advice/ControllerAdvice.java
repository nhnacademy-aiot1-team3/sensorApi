package live.databo3.sensor.advice;

import live.databo3.sensor.exception.already_exist_exception.AlreadyExistException;
import live.databo3.sensor.exception.not_exist_exception.NotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({AlreadyExistException.class})
    private ResponseEntity<ErrorResponse<ErrorHeader, ErrorBody>> alReadyExistExceptionHandler(Exception e) {
        ErrorHeader errorHeader = new ErrorHeader(101, "이미 존재합니다.");

        ErrorResponse<ErrorHeader, ErrorBody> errorResponse = new ErrorResponse<>(errorHeader, new ErrorBody(null));

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler({NotExistException.class})
    private ResponseEntity<ErrorResponse<ErrorHeader, ErrorBody>> notExistExceptionHandler(Exception e) {
        ErrorHeader errorHeader = new ErrorHeader(102, "존재하지 않습니다.");

        ErrorResponse<ErrorHeader, ErrorBody> errorResponse = new ErrorResponse<>(errorHeader, new ErrorBody(null));

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
