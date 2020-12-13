package Misc;

import Exceptions.*;
import Media.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

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

    public void createUser(String username, char[] password) throws UsernameTakenException {
        if (username.length() == 0) throw new IllegalArgumentException("Username must be of at least length 1.");
        else if (password.length == 0) throw new IllegalArgumentException("Password must be of at least length 1.");
        else if (users.containsKey(username)) throw new UsernameTakenException(username);
        else users.put(username, new User(username, encryptPassword(password)));
    }

    public void createUser(String username, String password) throws UsernameTakenException {
        createUser(username, password.toCharArray());
    }

    private char[] encryptPassword(char[] password) {
        for (int i = 0; i<password.length; i++) {
            int charVal = (int) password[i] + 5;
            password[i] = ((char) charVal);
        }
        return password;
    }

    public boolean login(String username, char[] password) throws InvalidUsernameException, InvalidPasswordException {
        if (!users.containsKey(username)) throw new InvalidUsernameException(username);
        else if (!Arrays.equals(users.get(username).getEncryptedPassword(), encryptPassword(password))) throw new InvalidPasswordException();
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
