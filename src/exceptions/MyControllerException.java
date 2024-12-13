package exceptions;

public class MyControllerException extends RuntimeException {
    public MyControllerException(String message) {
        super(message);
    }
}
