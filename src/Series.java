package src;

import java.awt.Image;

public class Series extends Media {
    private int[] seasons;

    public Series(String title, int release, double rating, Image poster, int[] seasons) {
        super(title, release, rating, poster);
        this.seasons = seasons;
    }

    public int[] getSeason() {
        return seasons;
    }
}
