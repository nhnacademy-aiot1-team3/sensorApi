package live.databo3.sensor.error_log.service;

import live.databo3.sensor.error_log.entity.ErrorLog;
import live.databo3.sensor.error_log.repository.ErrorLogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static reactor.core.publisher.Mono.when;

class ErrorLogServiceTest {
    @MockBean
    private ErrorLogRepository errorLogRepository;

    @InjectMocks
    private ErrorLogService errorLogService;


    @Test
    @DisplayName("에러 로그 생성 테스트")
    void errorLogCreateTest() {
    }
}