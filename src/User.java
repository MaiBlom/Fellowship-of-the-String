package src;

import src.Media.*;

import java.util.HashSet;
import java.util.Set;

public class User {
    protected String username;
    protected Set<Media> favorites;
    
    public User (String username) {
        this.username = username;
        this.favorites = new HashSet<>();
    }

    // getters
    public String getUsername() {
        return username;
    }
    public Set<Media> getFavorites() {
        return favorites;
    }

    public void favorite(Media m) {
        favorites.add(m);
    }
}
