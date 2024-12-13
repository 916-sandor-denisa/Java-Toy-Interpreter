package exceptions;

public class MyRepoException extends RuntimeException {
    public MyRepoException(String message) {
        super(message);
    }
}
