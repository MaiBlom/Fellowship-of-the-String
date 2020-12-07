package src;

import src.Exceptions.*;
import java.util.HashMap;

public class UserDB {
    private HashMap<String, String> users;
    private static UserDB instance = new UserDB();

    // This class is a singleton, because we will never need more than a single UserDB.
    private UserDB() {
        users = new HashMap<>();
        createUser("admin", "admin");
    }
    public static UserDB getInstance() {
        if(instance == null) instance = new UserDB();
        return instance;
    }

    public void createUser(String username, String password) throws UsernameTakenException {
        if (users.containsKey(username)) throw new UsernameTakenException(username);
        else users.put(username, encryptPassword(password));
    }
    private String encryptPassword(String s) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i<s.length(); i++) {
            int charVal = (int) s.charAt(i) + 5;
            encrypted.append((char) charVal);
        }
        return encrypted.toString();
    }

    public boolean login(String username, String password) throws InvalidUsernameException, InvalidPasswordException {
        if (!users.containsKey(username)) throw new InvalidUsernameException(username);
        else if (!users.get(username).equals(encryptPassword(password))) throw new InvalidPasswordException();
        else return true;
    }
}