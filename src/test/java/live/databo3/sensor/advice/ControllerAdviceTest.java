package live.databo3.sensor.advice;

import live.databo3.sensor.exception.UnAuthorizedAccessException;
import live.databo3.sensor.exception.already_exist_exception.AlreadyExistException;
import live.databo3.sensor.exception.not_exist_exception.NotExistException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ControllerAdviceTest {

    @InjectMocks
    private ControllerAdvice controllerAdvice;

    @Test
    void testAlReadyExistExceptionHandler() {
        AlreadyExistException exception = new AlreadyExistException("testElement", "testMessage");

        ResponseEntity<ErrorResponse<ErrorHeader, ErrorBody>> responseEntity = controllerAdvice.alReadyExistExceptionHandler(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(101, responseEntity.getBody().getHeader().getResultCode());
        assertEquals("이미 존재합니다.", responseEntity.getBody().getHeader().getResultMessage());
    }

    @Test
    void testNotExistExceptionHandler() {
        NotExistException exception = new NotExistException("testElement", "testMessage");

        ResponseEntity<ErrorResponse<ErrorHeader, ErrorBody>> responseEntity = controllerAdvice.notExistExceptionHandler(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(102, responseEntity.getBody().getHeader().getResultCode());
        assertEquals("존재하지 않습니다.", responseEntity.getBody().getHeader().getResultMessage());
    }

    @Test
    void testUnAuthorizedAccessExceptionHandler() {
        UnAuthorizedAccessException exception = new UnAuthorizedAccessException("허가되지 않은 접근입니다.");

        ResponseEntity<ErrorResponse<ErrorHeader, ErrorBody>> responseEntity = controllerAdvice.unAuthorizedAccessExceptionHandler(exception);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals(103, responseEntity.getBody().getHeader().getResultCode());
        assertEquals("허가되지 않은 접근입니다.", responseEntity.getBody().getHeader().getResultMessage());
    }
}
