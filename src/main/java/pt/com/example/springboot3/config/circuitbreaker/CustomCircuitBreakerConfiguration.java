package pt.com.example.springboot3.config.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.com.example.springboot3.config.ApiConfigProperties;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class CustomCircuitBreakerConfiguration {

    private final ApiConfigProperties resilience4jProperties;

    @Bean
    public CircuitBreaker circuitBreaker() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(resilience4jProperties.getCircuitBreaker().getSlidingWindowSize())
                .failureRateThreshold(resilience4jProperties.getCircuitBreaker().getFailureRateThreshold())
                .maxWaitDurationInHalfOpenState(Duration.ofSeconds(resilience4jProperties.getCircuitBreaker().getMaxWaitDurationInHalfOpenState()))
                .build();

        return CircuitBreaker.of("requestHandlerCircuitBreaker", circuitBreakerConfig);
    }

}

