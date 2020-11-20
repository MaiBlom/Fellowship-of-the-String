package src;
import java.util.Set;

import src.Media.*;

import java.util.HashSet;

public class Genre {
    protected Set<Media> media;

    public Genre() {
        media = new HashSet<>();
    }

    // getters
    public Set<Media> getMedia() {
        return media;
    }
    public Set<Media> getMovies() {
        Set<Media> movies = new HashSet<>();
        for (Media m : media) {
            if (m instanceof Movie) movies.add(m);
        }
        return movies;
    }
    public Set<Media> getSeries() {
        Set<Media> series = new HashSet<>();
        for (Media m : media) {
            if (m instanceof Series) series.add(m);
        }
        return series;
    }

    public void add(Media m) {
        media.add(m);
    }
}
