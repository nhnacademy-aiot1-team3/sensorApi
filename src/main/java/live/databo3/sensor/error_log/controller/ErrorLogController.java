package live.databo3.sensor.error_log.controller;

import live.databo3.sensor.error_log.dto.ErrorLogCreateRequestDto;
import live.databo3.sensor.error_log.dto.ErrorLogResponseDto;
import live.databo3.sensor.error_log.service.ErrorLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/error/log")
@RequiredArgsConstructor
public class ErrorLogController {
    private final ErrorLogService errorLogService;

    @PostMapping
    public ResponseEntity<ErrorLogResponseDto> saveErrorLog(@RequestBody ErrorLogCreateRequestDto errorLogCreateRequestDto) {
        ErrorLogResponseDto errorLog = errorLogService.createErrorLog(errorLogCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(errorLog);
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<Void> deleteErrorLog(@PathVariable Long logId) {
        errorLogService.deleteErrorLog(logId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/org/{organizationId}")
    public ResponseEntity<List<ErrorLogResponseDto>> getErrorLogList(@PathVariable Integer organizationId) {
        List<ErrorLogResponseDto> errorLogs = errorLogService.getErrorLogs(organizationId);
        return ResponseEntity.status(HttpStatus.OK).body(errorLogs);
    }
}
