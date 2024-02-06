package pt.com.example.springboot3.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "request-handler")
public class ApiConfigProperties {

    private Retry retry = new Retry();
    private CircuitBreaker circuitBreaker = new CircuitBreaker();

    @Getter
    @Setter
    public class Retry {
        private int maxAttempts;
        private Long waitDuration;
    }

    @Getter
    @Setter
    public class CircuitBreaker {
        private int slidingWindowSize;
        private Long maxWaitDurationInHalfOpenState;
        private float failureRateThreshold;
    }

}
