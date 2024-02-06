package pt.com.example.springboot3.config.feign;

import feign.Client;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pt.com.example.springboot3.config.circuitbreaker.CircuitBreakerFeignClient;

@Configuration
@Import(FeignClientsConfiguration.class)
public class FeignConfiguration {

    @Bean
    public Client feignClient(CircuitBreaker circuitBreaker) {
        return new CircuitBreakerFeignClient(new Client.Default(null, null), circuitBreaker);
    }
}

