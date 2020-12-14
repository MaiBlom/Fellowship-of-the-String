package Model;

import java.awt.image.*;

public class Movie extends Media {
    
    public Movie(String title, int release, String[] genres, double rating, BufferedImage image) {
        super(title, release, genres, rating, image);
    }
}