package src;

import java.awt.Image;

public abstract class Media {
    protected String title;
    protected int release;
    protected double rating; 
    protected Image poster;

    public Media(String title, int release, double rating, Image poster) {
        this.title = title;
        this.release = release;
        this.rating = rating;
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public int getRelease() {
        return release; //smile
    }

    public double getRating() {
        return rating;
    }

    public Image getPoster() {
        return poster;
    }
}
