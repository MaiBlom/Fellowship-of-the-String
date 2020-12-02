package src;

import src.Media.*;

import java.util.HashSet;
import java.util.HashMap;

public class User {
    protected String username;
    protected HashSet<Media> favorites;
    protected HashMap<Media, Double> ratings;
    
    public User (String username) {
        this.username = username;
        this.favorites = new HashSet<>();
    }

    // getters
    public String getUsername() {
        return username;
    }
    public HashSet<Media> getFavorites() {
        return favorites;
    }

    public boolean isFavorite(Media m) {
        return favorites.contains(m);
    }

    public void favorite(Media m) {
        favorites.add(m);
    }

    public void rate(Media m, double rating) {
        ratings.put(m, rating);
    }
    public double getRating(Media m) {
        return ratings.get(m);
    }
}
