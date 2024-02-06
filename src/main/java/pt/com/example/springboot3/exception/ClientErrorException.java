package pt.com.example.springboot3.exception;

public class ClientErrorException extends RuntimeException {
    public ClientErrorException(Throwable cause) {
        super(cause);
    }
}
