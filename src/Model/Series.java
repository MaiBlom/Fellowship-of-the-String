package Model;

import java.awt.image.*;

public class Series extends Media {
    private final String runtime;
    private final int[] seasons;

    public Series(String title, int release, String runtime, String[] genres, double rating, BufferedImage poster, int[] seasons) {
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