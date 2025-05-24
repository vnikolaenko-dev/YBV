package org.example.backend.controller.actuator;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metrics")
public class CustomMetricsController {
    private final Counter customCounter;

    public CustomMetricsController(MeterRegistry registry) {
        this.customCounter = Counter.builder("custom.metric")
                .description("Custom business metric")
                .register(registry);
    }

    @GetMapping("/api/action")
    public String triggerAction(@RequestHeader("Authorization") String authHeader) {
        customCounter.increment();
        return "Action triggered!";
    }
}
