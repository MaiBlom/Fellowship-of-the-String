package src;

import src.Media.*;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    protected String username;
    protected ArrayList<Media> favorites;
    protected HashMap<Media, Double> ratings;
    
    public User (String username) {
        this.username = username;
        this.favorites = new ArrayList<>();
        this.ratings = new HashMap<>();
    }

    // getters
    public String getUsername() {
        return username;
    }
    public ArrayList<Media> getFavorites() {
        return favorites;
    }

    public boolean isFavorite(Media m) {
        return favorites.contains(m);
    }

    public void favorite(Media m) {
        if (!favorites.contains(m)) favorites.add(m);
    }

    public void unfavorite(Media m) {
        if (favorites.contains(m)) favorites.remove(m);
    }

    public void rate(Media m, double rating) {
        ratings.put(m, rating);
    }

    public double getRating(Media m) {
        return ratings.get(m);
    }
}
