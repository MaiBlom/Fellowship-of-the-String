package src;
import java.util.Locale;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.*;
import src.Media.*;

public class FileReader {
    public FileReader(MediaDB db){
        Scanner scMovie = new Scanner(getClass().getClassLoader().getResourceAsStream("res/movies.txt"));
        Scanner scSeries = new Scanner(getClass().getClassLoader().getResourceAsStream("series.txt"));

        scMovie.useDelimiter("\\s*;\\s*,\\s*");
        scMovie.useLocale(Locale.ENGLISH);
        scSeries.useDelimiter("\\s*;\\s*,\\s*");
        scSeries.useLocale(Locale.ENGLISH);

        while(scMovie.hasNext()){
            String title = scMovie.next();
            int release = scMovie.nextInt();
            double rating = scMovie.nextDouble();
            File file = new File("res/filmplakater/"+title+".jpg");
            BufferedImage image;
            try{
                image = ImageIO.read(file);
            }catch(IOException e){
                image = null;
            }
            Movie movie = new Movie(title, release, rating, image);
            db.add(movie);
        }
        while(scSeries.hasNext()){
            String title = scSeries.next();
            int release = scSeries.nextInt();
            double rating = scSeries.nextDouble();
            File file = new File("res/serieforsider/"+title+".jpg");
            BufferedImage image;
            try{
                image = ImageIO.read(file);
            }catch(IOException e){
                image = null;
            }
            String[] seasons = scSeries.next().split(", ");
            int[] episodes = new int[seasons.length];
            for(int i = 0; i<episodes.length; i++){
                String s = seasons[i].split("-")[1];
                episodes[i] = Integer.parseInt(s);
            }
            Series series = new Series(title, release, rating, image, episodes);
            db.add(series);
        }
    }
}