package Model;

import java.util.HashMap;

public class UserDB {
    private final HashMap<String, User> users;
    private static UserDB instance = new UserDB();

    // This class is a singleton, because we will never need more than a single UserDB.
    private UserDB() {
        users = new HashMap<>();
    }
    public static UserDB getInstance() {
        if(instance == null) instance = new UserDB();
        return instance;
    }

    public HashMap<String, User> getUsers() {
        return users;
    }
}
