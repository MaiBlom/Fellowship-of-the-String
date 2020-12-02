package src.Media;

import java.awt.image.*;


public abstract class Media {
    protected String title;
    protected int release;
    protected String[] genres;
    protected double rating; 
    protected BufferedImage poster;

    public Media(String title, int release, String[] genres, double rating, BufferedImage poster) {
        this.title = title;
        this.release = release;
        this.genres = genres;
        this.rating = rating;
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }
    public int getRelease() {
        return release; //smile
    }
    public String[] getGenres() {
        return genres;
    }
    public double getRating() {
        return rating;
    }
    public BufferedImage getPoster() {
        return poster;
    }
}
