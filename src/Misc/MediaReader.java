package Misc;

import Media.*;
import java.util.Locale;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.*;

public class MediaReader {
    private MediaDB db;
    private static MediaReader instance;

    // This class is a singleton, because we will never need more than a single MediaReader.
    private MediaReader(MediaDB db) {
        this.db = db;
        read(false);
        read(true);
    }
    //Ændret til ikke bare at overskrive instance. (Singleton)
    public static MediaReader getInstance(MediaDB db) {
        if (instance == null) instance = new MediaReader(db);
        return instance;
    }

    // The read() method takes a single boolean parameter to know whether it's reading series or movies.
    // Throughout the method, it will check different things depending on whether it's reading series
    // or movies (e.g. it won't read episodes, if it's currently reading movies). 
    private void read(boolean isSeries) {
        // Initialise the scanner with the correct file depending on whether 
        // we're reading movies or series.
        Scanner sc;
        if (isSeries) {
            sc = new Scanner(getClass().getClassLoader().getResourceAsStream("series.txt"));
        } else {
            sc = new Scanner(getClass().getClassLoader().getResourceAsStream("movies.txt"));
        }
        sc.useDelimiter("\\s*;\\s*");
        sc.useLocale(Locale.FRANCE);

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
            //File file;
            //Er det bedre ikke at læse dem ind her, men at have billederne med i projektet, også bare referere til dem i GUI i stedet?
            //Sammensætningen af medie objekt og billedet skal ske i controlleren
            //BufferedInputStream er en mulighed??
            BufferedImage image;
            String path;
            try {
                if (isSeries) {
                    if (title.contains("Scener ur ett")) image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("serieforsider/Scener ur ett aktenskap.jpg"));
                    else image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("serieforsider/"+title+".jpg"));
                } else {
                    image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("filmplakater/"+title+".jpg"));
                }
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
        }
    }
}