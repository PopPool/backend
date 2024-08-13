package com.application.poppool.domain.aws.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HealthCheckController {

    @GetMapping("/health-check")
    public ResponseEntity<String> healthcheck() {
        return ResponseEntity.ok("OK");
    }

}
