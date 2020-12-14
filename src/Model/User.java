package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    protected String username;
    protected char[] encryptedPassword;
    protected ArrayList<Media> favoritesList;
    protected HashMap<Media, Double> ratings;
    
    public User (String username, char[] encryptedPassword) {
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.favoritesList = new ArrayList<>();
        this.ratings = new HashMap<>();
    }

    // Getters
    public String getUsername() {
        return username;
    }
    public char[] getEncryptedPassword() {
        return encryptedPassword;
    }
    public ArrayList<Media> getFavorites(){
        return favoritesList;
    }
    
    public boolean isFavorite(Media m){
        return favoritesList.contains(m);
    }

    public double getRating(Media m) {
        return ratings.get(m);
    }

    //Setters
    public void favorite(Media m) {
        favoritesList.add(m);
    }

    public void unfavorite(Media m) {
        favoritesList.remove(m);
    }
    public void rate(Media m, double rating) {
        ratings.put(m, rating);
    }
}