package Exceptions;

public class InvalidUsernameException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidUsernameException(String username) {
        super("Invalid username: " + username);
    }
}