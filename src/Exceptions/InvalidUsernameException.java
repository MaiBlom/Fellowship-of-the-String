package src.Exceptions;

public class InvalidUsernameException extends RuntimeException {
    private String username;

    public InvalidUsernameException(String username) {
        super("Invalid username: " + username);
        this.username = username;
    }
}
