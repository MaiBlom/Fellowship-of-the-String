package Exceptions;

public class UsernameTakenException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsernameTakenException(String username) {
        super("The username \"" + username + "\" is not available.");
    }
}