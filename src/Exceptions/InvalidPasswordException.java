package Exceptions;

public class InvalidPasswordException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidPasswordException() {
        super("Invalid password");
    }
}