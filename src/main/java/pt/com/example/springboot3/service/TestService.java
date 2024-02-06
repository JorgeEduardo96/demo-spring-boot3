package pt.com.example.springboot3.service;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pt.com.example.springboot3.client.GeoApiClient;
import pt.com.example.springboot3.client.RequestHandlerClient;
import pt.com.example.springboot3.config.retry.RetryConfiguration;
import pt.com.example.springboot3.dtos.MunicipioRecord;
import pt.com.example.springboot3.exception.ClientErrorException;

import java.util.function.Supplier;

@Service
@Slf4j
public class TestService {

    private final GeoApiClient geoApiClient;
    private final RequestHandlerClient requestHandlerClient;
    private final CircuitBreaker circuitBreaker;
    private final Retry retry;
    private final RetryConfiguration retryConfiguration;

    public TestService(GeoApiClient geoApiClient, RetryConfiguration retryConfiguration, RequestHandlerClient requestHandlerClient) {
        this.geoApiClient = geoApiClient;
        this.circuitBreaker = CircuitBreaker.ofDefaults("requestHandlerCircuitBreaker");
        this.retryConfiguration = retryConfiguration;
        this.retry = Retry.of("requestHandlerRetry", this.retryConfiguration.createDefaultRetryConfig());
        this.requestHandlerClient = requestHandlerClient;
    }

    public MunicipioRecord getMunicipio(String municipio) {
        try {
            return executeWithRetryAndCircuitBreaker(() -> geoApiClient.getMunicipio(municipio));
        } catch (Exception ex) {
            logExceptionTrace(ex);
            throw new ClientErrorException(ex);
        }
    }

    public String ok() {
        try {
            return executeWithRetryAndCircuitBreaker(requestHandlerClient::ok);
        } catch (Exception ex) {
            logExceptionTrace(ex);
            throw new ClientErrorException(ex);
        }
    }

    private <T> T executeWithRetryAndCircuitBreaker(Supplier<T> supplier) {
        Supplier<T> retrySupplier = Retry.decorateSupplier(retry, () ->
                CircuitBreaker.decorateSupplier(circuitBreaker, supplier).get()
        );
        return retrySupplier.get();
    }

    private void logExceptionTrace(Exception ex) {
        log.info("State of circuit breaker: {}", circuitBreaker.getState());
        log.error("Error when calling external function", ex);
    }

}
