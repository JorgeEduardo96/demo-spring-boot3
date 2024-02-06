package pt.com.example.springboot3.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers, HttpStatusCode status,
                                                                      WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<Object> handleClientErrorException(ClientErrorException ex, WebRequest request) {
        Problem problem = Problem.builder()
                .details(ex.getMessage())
                .timestamp(OffsetDateTime.now())
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.REQUEST_TIMEOUT, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatusCode status, WebRequest request) {
        if (body instanceof String) {
            body = Problem.builder()
                    .details((String) body)
                    .timestamp(OffsetDateTime.now())
                    .build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Getter
    @Setter
    @Builder
    public static class Problem {
        private String details;
        private OffsetDateTime timestamp;
    }

}
