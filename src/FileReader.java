package src;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.*;
import src.Media.*;
import java.util.Set;

public class FileReader {
    protected Scanner scMovie;
    protected Scanner scSeries;
    protected MediaDB db;

    public FileReader(MediaDB db){
        scMovie = new Scanner(getClass().getClassLoader().getResourceAsStream("res/movies.txt"));
        scSeries = new Scanner(getClass().getClassLoader().getResourceAsStream("res/series.txt"));
        this.db = db;

        scMovie.useDelimiter("\\s*;\\s*");
        scMovie.useLocale(Locale.FRANCE);
        scSeries.useDelimiter("\\s*;\\s*");
        scSeries.useLocale(Locale.FRANCE);
    }

    public void read() {
        while(scMovie.hasNext()){
            String title = scMovie.next();
            int release = scMovie.nextInt();

            // Reads everything until next semi-colon as a single string.
            // Split the string at every comma to get multiple strings with 
            // the different genres.
            String[] genres = scMovie.next().split(", ");

            double rating = scMovie.nextDouble();

            // Finds the .jpg file with the title of the movie and create
            // buffered image with that file.
            File file = new File("res/filmplakater/"+title+".jpg");
            BufferedImage image;
            try{
                image = ImageIO.read(file);
            }catch(IOException e){
                image = null;
            }

            Movie movie = new Movie(title, release, genres, rating, image);
            db.add(movie);
        }

        while(scSeries.hasNext()){
            String title = scSeries.next();

            // Read everything until next semi-colon as a single string.
            // Split the string at the dash in the middle, and parse the
            // first string as an integer for the release year.
            String runtime = scSeries.next();
            String[] releaseAndEnd = runtime.split("-");
            int release = Integer.parseInt(releaseAndEnd[0]);

            // Reads everything until next semi-colon as a single string.
            // Split the string at every comma to get multiple strings with 
            // the different genres.
            String[] genres = scSeries.next().split(", ");

            double rating = scSeries.nextDouble();

            // Finds the .jpg file with the title of the movie and create
            // buffered image with that file.
            File file = new File("res/serieforsider/"+title+".jpg");
            BufferedImage image;
            try{
                image = ImageIO.read(file);
            }catch(IOException e){
                image = null;
            }

            // Reads everything until next semi-colon as a single string.
            // Split the string at every comma to get multiple strings with 
            // the different seasons and episodes. 
            String[] seasons = scSeries.next().split(", ");
            int[] episodes = new int[seasons.length];
            // Read through the string with format <season number>-<number of episodes>.
            // Split the string at the dash, and save the number of episodes in the int[]
            for(int i = 0; i<episodes.length; i++){
                String s = seasons[i].split("-")[1];
                episodes[i] = Integer.parseInt(s);
            }

            Series series = new Series(title, release, runtime, genres, rating, image, episodes);
            db.add(series);
        }
    }
}