package org.example.backend.util.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.example.backend.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class UserMetrics {
    private final AtomicInteger userCount = new AtomicInteger(0);

    public UserMetrics(MeterRegistry registry, UserRepository userRepository) {
        // Инициализируем начальное значение
        userCount.set((int) userRepository.count());

        // Регистрируем метрику
        Gauge.builder("app.users", userCount, AtomicInteger::get)
                .description("Total number of registered users")
                .strongReference(true) // Важно для Gauges!
                .register(registry);
    }

    public void incrementUserCount() {
        userCount.incrementAndGet();
    }

    public void decrementUserCount() {
        userCount.decrementAndGet();
    }
}
