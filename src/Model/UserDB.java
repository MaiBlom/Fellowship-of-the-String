package Model;

import java.util.HashMap;

public class UserDB {
    private HashMap<String, User> users;
    private static UserDB instance = new UserDB();

    // This class is a singleton, because we will never need more than a single UserDB.
    private UserDB() {
        users = new HashMap<>();
    }
    public static UserDB getInstance() {
        if(instance == null) instance = new UserDB();
        return instance;
    }

    public char[] encryptPassword(char[] password) {
        for (int i = 0; i<password.length; i++) {
            int charVal = (int) password[i] + 5;
            password[i] = ((char) charVal);
        }
        return password;
    }

    public HashMap<String, User> getUsers() {
        return users;
    }
}
