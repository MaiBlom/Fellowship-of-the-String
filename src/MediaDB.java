package src;

import src.Media.*;

import java.util.HashMap;
import java.util.ArrayList;

public class MediaDB {
    private ArrayList<Media> allMedia;
    private HashMap<String, Genre> genres;
    private static MediaDB instance = new MediaDB();

    // This class is a singleton, because we will never need more than a single MediaDB.
    private MediaDB() {
        allMedia = new ArrayList<>();
        genres = new HashMap<>();
        createGenreHashMap();

        MediaReader fr = MediaReader.getInstance(this);
        fr.readAll();
    }
    public static MediaDB getInstance() {
        if(instance == null) instance = new MediaDB();
        return instance;
    }

    // getters
    public ArrayList<Media> getAllMedia() {
        return allMedia;
    }
    public ArrayList<Media> getMovies() {
        ArrayList<Media> movies = new ArrayList<>();
        for (Media m : allMedia) {
            if (m instanceof Movie) movies.add(m);
        }
        return movies;
    }
    public ArrayList<Media> getSeries() {
        ArrayList<Media> series = new ArrayList<>();
        for (Media m : allMedia) {
            if (m instanceof Series) series.add(m);
        }
        return series;
    }
    public HashMap<String, Genre> getAllGenres() {
        return genres;
    }


    public void add(Media m) {
        allMedia.add(m);
    }

    private void createGenreHashMap() {
        genres.put("Crime", new Genre("Crime"));
        genres.put("Drama", new Genre("Drama"));
        genres.put("Biography", new Genre("Biography"));
        genres.put("Sport", new Genre("Sport"));
        genres.put("History", new Genre("History"));
        genres.put("Romance", new Genre("Romance"));
        genres.put("War", new Genre("War"));
        genres.put("Mystery", new Genre("Mystery"));
        genres.put("Adventure", new Genre("Adventure"));
        genres.put("Family", new Genre("Family"));
        genres.put("Fantasy", new Genre("Fantasy"));
        genres.put("Thriller", new Genre("Thriller"));
        genres.put("Horror", new Genre("Horror"));
        genres.put("Film-Noir", new Genre("Film-Noir"));
        genres.put("Action", new Genre("Action"));
        genres.put("Sci-fi", new Genre("Sci-fi"));
        genres.put("Comedy", new Genre("Comedy"));
        genres.put("Musical", new Genre("Musical"));
        genres.put("Western", new Genre("Western"));
        genres.put("Music", new Genre("Music"));
        genres.put("Talk-show", new Genre("Talk-show"));
        genres.put("Documentary", new Genre("Documentary"));
        genres.put("Animation", new Genre("Animation"));
    }
}
