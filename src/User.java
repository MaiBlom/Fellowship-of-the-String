package src;

import src.Media.*;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    protected String username;
    protected char[] encryptedPassword;
    protected ArrayList<Media> favoriteMovies;
    protected ArrayList<Media> favoriteSeries;
    protected HashMap<Media, Double> ratings;
    
    public User (String username, char[] encryptedPassword) {
        this.username = username;
        this.encryptedPassword = encryptedPassword;
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
    public char[] getEncryptedPassword() {
        return encryptedPassword;
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

    public double getRating(Media m) {
        return ratings.get(m);
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
}
