package src;

import src.Media.*;

import java.util.ArrayList;

public class MediaDB {
    private ArrayList<Media> allMedia;
    private static MediaDB instance = new MediaDB();

    // This class is a singleton, because we will never need more than a single MediaDB.
    private MediaDB() {
        allMedia = new ArrayList<>();

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


    public void add(Media m) {
        allMedia.add(m);
    }
}
