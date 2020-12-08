package src;

import src.Exceptions.*;
import src.Media.*;
import java.util.HashMap;
import java.util.ArrayList;

public class UserDB {
    private HashMap<String, User> users;
    private static UserDB instance = new UserDB();

    // This class is a singleton, because we will never need more than a single UserDB.
    private UserDB() {
        users = new HashMap<>();
        setupStartingUsers();
    }
    public static UserDB getInstance() {
        if(instance == null) instance = new UserDB();
        return instance;
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public void createUser(String username, String password) throws UsernameTakenException {
        if (users.containsKey(username)) throw new UsernameTakenException(username);
        else users.put(username, new User(username, encryptPassword(password)));
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
        else if (!users.get(username).getEncryptedPassword().equals(encryptPassword(password))) throw new InvalidPasswordException();
        else return true;
    }

    private void setupStartingUsers() {
        MediaDB db = MediaDB.getInstance();
        ArrayList<Media> movies = db.getMovies();
        ArrayList<Media> series = db.getSeries();

        createUser("test", "test");
        User test = getUser("test");
        test.favorite(movies.get(1));
        test.favorite(movies.get(9));
        test.favorite(movies.get(63));
        test.favorite(series.get(24));
        test.favorite(series.get(55));
        test.favorite(series.get(73));
    }
}