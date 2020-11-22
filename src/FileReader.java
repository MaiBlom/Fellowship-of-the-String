package src;

import src.Media.*;

import java.util.Locale;
import java.util.Scanner;
import java.util.HashMap;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.*;

public class FileReader {
    private MediaDB db;
    private static FileReader instance;

    // This class is a singleton, because we will never need more than a single FileReader.
    private FileReader(MediaDB db){
        this.db = db;
    }
    public static FileReader getInstance(MediaDB db) {
        instance = new FileReader(db);
        return instance;
    }

    // readAll() will call read(isSeries = false) (all movies) and then read(isSeries = true) (all series).
    public void readAll() {
        instance.read(false);
        instance.read(true);
    }

    // The read() method takes a single boolean parameter to know whether it's reading series or movies.
    // Throughout the method, it will check different things depending on whether it's reading series
    // or movies (e.g. it won't read episodes, if it's currently reading movies). 
    private void read(boolean isSeries) {
        // Initialise the scanner with the correct file depending on whether 
        // we're reading movies or series.
        Scanner sc;
        if (isSeries) {
            sc = new Scanner(getClass().getClassLoader().getResourceAsStream("res/series.txt"));
        } else {
            sc = new Scanner(getClass().getClassLoader().getResourceAsStream("res/movies.txt"));
        }
        sc.useDelimiter("\\s*;\\s*");
        sc.useLocale(Locale.FRANCE);

        // Get genres to add media to them.
        HashMap<String, Genre> allGenres = db.getAllGenres();

        while(sc.hasNext()){
            String title = sc.next();
            String runtime;
            int release;

            if (isSeries) {
                // Read everything until next semi-colon as a single string. Split the string at the dash in 
                // the middle, and parse the first string as an integer for the release year.
                runtime = sc.next();
                String[] releaseAndEnd = runtime.split("-");
                release = Integer.parseInt(releaseAndEnd[0]);
            } else {
                runtime = null;
                release = sc.nextInt();
            }

            // Reads everything until next semi-colon as a single string. Split the string at every comma to get 
            // multiple strings with the different genres.
            String[] genres = sc.next().split(", ");

            double rating = sc.nextDouble();

            // Finds the .jpg file with the title of the movie or series and create BufferedImage with that file.
            File file;
            if (isSeries) {
                file = new File("res/serieforsider/"+title+".jpg");
            } else {
                file = new File("res/filmplakater/"+title+".jpg");
            }

            BufferedImage image;
            try {
                image = ImageIO.read(file);
            } catch (IOException e) {
                image = null;
            }

            int[] episodes;
            if (isSeries) {
                // Reads everything until next semi-colon as a single string.  Split the string at every comma 
                // to get multiple strings with the different seasons and episodes. 
                String[] seasons = sc.next().split(", ");
                episodes = new int[seasons.length];
                // Read through the string with format <season number>-<number of episodes>. 
                // Split the string at the dash, and save the number of episodes in the int[]
                for(int i = 0; i<episodes.length; i++){
                    String s = seasons[i].split("-")[1];
                    episodes[i] = Integer.parseInt(s);
                }
            } else {
                episodes = null;
            }
            
            Media media;
            if (isSeries) {
                media = new Series(title, release, runtime, genres, rating, image, episodes);
            } else {
                media = new Movie(title, release, genres, rating, image);
            }
            
            db.add(media); 

            // Add media object to the HashSet in the Genre objects.
            for (int i = 0; i<genres.length; i++) {
                allGenres.get(genres[i]).add(media);
            }
        }
    }
}
