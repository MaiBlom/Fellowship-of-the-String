package src;

import src.Media.*;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    protected String username;
    protected ArrayList<Media> favoriteMovies;
    protected ArrayList<Media> favoriteSeries;
    protected HashMap<Media, Double> ratings;
    
    public User (String username) {
        this.username = username;
        this.favoriteMovies = new ArrayList<>();
        this.favoriteSeries = new ArrayList<>();
        this.ratings = new HashMap<>();
    }

    // Getters
    public String getUsername() {
        return username;
    }
    public ArrayList<Media> getFavoriteMovies() {
        return favoriteMovies;
    }

    public boolean isFavorite(Media m) {
        return isFavoriteMovie(m) || isFavoriteSeries(m);
    }

    public ArrayList<Media> getFavoriteSeries() {
        return favoriteSeries;
    }

    private boolean isFavoriteMovie(Media m) {
        return favoriteMovies.contains(m);
    }

    private boolean isFavoriteSeries(Media m) {
        return favoriteSeries.contains(m);
    }

    //Setters
    public void favorite(Media m) {
        if (m instanceof Movie && !favoriteMovies.contains(m)) favoriteMovies.add(m);
        if (m instanceof Series && !favoriteSeries.contains(m)) favoriteSeries.add(m);
    }

    public void unfavorite(Media m) {
        if (m instanceof Movie && favoriteMovies.contains(m)) favoriteMovies.remove(m);
        if (m instanceof Series && favoriteSeries.contains(m)) favoriteSeries.remove(m);
    }

    public void rate(Media m, double rating) {
        ratings.put(m, rating);
    }

    public double getRating(Media m) {
        return ratings.get(m);
    }
}
