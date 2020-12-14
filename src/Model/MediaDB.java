package Model;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;

public class MediaDB {
    private ArrayList<Media> allMedia;
    private ArrayList<Media> recommended;
    private static MediaDB instance;

    // This class is a singleton, because we will never need more than a single MediaDB.
    private MediaDB() {
        allMedia = new ArrayList<>();
        recommended = new ArrayList<>();
        MediaReader.getInstance(this);
        makeRecommended();
    }
    public static MediaDB getInstance() {
        if(instance == null) instance = new MediaDB();
        return instance;
    }

    private void makeRecommended() {
        try {
            Movie fellowship = new Movie("The Lord of the Rings: The Fellowship of the Ring", 2001, new String[] { "Action", "Adventure", "Drama" },
                    8.8, ImageIO.read(getClass().getClassLoader().getResourceAsStream("redaktionfilm/fellowship of the ring.jpg")));
            Movie twoTowers = new Movie("The Lord of the Rings: The Two Towers", 2002, new String[] { "Action", "Adventure", "Drama" },
                    8.7, ImageIO.read(getClass().getClassLoader().getResourceAsStream("redaktionfilm/two towers.jpg")));
            Movie returnKing = (Movie) getMovies().get(32);

            recommended.add(fellowship);
            recommended.add(twoTowers);
            recommended.add(returnKing);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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
    public ArrayList<Media> getRecommended() {
        return recommended;
    }


    public void add(Media m) {
        allMedia.add(m);
    }
}