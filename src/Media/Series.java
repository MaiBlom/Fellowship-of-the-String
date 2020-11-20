package src.Media;

import java.awt.Image;

public class Series extends Media {
    private String runtime;
    private int[] seasons;

    public Series(String title, int release, String runtime, String[] genres, double rating, Image poster, int[] seasons) {
        super(title, release, genres, rating, poster);
        this.runtime = runtime;
        this.seasons = seasons;
    }

    public int[] getSeason() {
        return seasons;
    }

    public String getRuntime() {
        return runtime;
    }
}
