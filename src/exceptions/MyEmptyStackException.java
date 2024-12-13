package exceptions;

public class MyEmptyStackException extends Exception {
    String message;

    public MyEmptyStackException(String message) {
        super(message);
    }
}
