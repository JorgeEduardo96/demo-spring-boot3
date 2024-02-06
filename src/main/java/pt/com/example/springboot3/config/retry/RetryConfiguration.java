package pt.com.example.springboot3.config.retry;

import io.github.resilience4j.retry.RetryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.com.example.springboot3.config.ApiConfigProperties;

import java.time.Duration;

@Component
public class RetryConfiguration {

    @Autowired
    private ApiConfigProperties apiConfigProperties;

    public RetryConfig createDefaultRetryConfig() {
        return RetryConfig.custom()
                .maxAttempts(apiConfigProperties.getRetry().getMaxAttempts())
                .waitDuration(Duration.ofSeconds(apiConfigProperties.getRetry().getWaitDuration()))
                .build();
    }

}
