package pt.com.example.springboot3.config.circuitbreaker;

import feign.Client;
import feign.Request;
import feign.Response;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;

import java.io.IOException;

public class CircuitBreakerFeignClient implements Client {
    private final Client delegate;
    private final CircuitBreaker circuitBreaker;

    public CircuitBreakerFeignClient(Client delegate, CircuitBreaker circuitBreaker) {
        this.delegate = delegate;
        this.circuitBreaker = circuitBreaker;
    }

    @Override
    public Response execute(Request request, Request.Options options) throws IOException {
        return circuitBreaker.executeSupplier(() -> {
            try {
                return delegate.execute(request, options);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao executar a chamada Feign.", e);
            }
        });
    }
}
