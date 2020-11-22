package src;

import src.Media.*;

import java.util.HashSet;

public class Genre {
    protected String name;
    protected HashSet<Media> media;

    public Genre(String name) {
        this.name = name;
        media = new HashSet<>();
    }

    // getters
    public String getName() {
        return name;
    }
    public HashSet<Media> getMedia() {
        return media;
    }
    public HashSet<Media> getMovies() {
        HashSet<Media> movies = new HashSet<>();
        for (Media m : media) {
            if (m instanceof Movie) movies.add(m);
        }
        return movies;
    }
    public HashSet<Media> getSeries() {
        HashSet<Media> series = new HashSet<>();
        for (Media m : media) {
            if (m instanceof Series) series.add(m);
        }
        return series;
    }

    public void add(Media m) {
        media.add(m);
    }
}
