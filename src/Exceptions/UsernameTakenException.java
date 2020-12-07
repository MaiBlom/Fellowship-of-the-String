package src.Exceptions;

public class UsernameTakenException extends RuntimeException {
    private String username;

    public UsernameTakenException(String username) {
        super("The username \"" + username + "\" is not available.");
        this.username = username;
    }
}
