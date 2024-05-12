package live.databo3.sensor.error_log.controller;

import com.netflix.discovery.converters.Auto;
import live.databo3.sensor.error_log.service.ErrorLogService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@WebMvcTest(ErrorLogController.class)
class ErrorLogControllerTest {

    @MockBean
    private ErrorLogService errorLogService;

    @Autowired
    MockMvc mockMvc;


    @Test
    @DisplayName("에러 로그 생성 테스트")
    void createErrorLog() throws Exception {
    }
}