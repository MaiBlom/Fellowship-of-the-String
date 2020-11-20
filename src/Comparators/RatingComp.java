package src.Comparators;

import java.util.Comparator;
import src.Media;

/* 
 * This class is used for comparing to Media objects
 * when sorting collections by rating. This comparator 
 * will sort media highest rating to lowest.
 */ 

public class RatingComp implements Comparator<Media> {
    public int compare(Media a, Media b) {
        if (a.getRating()-b.getRating() < 0) return 1;
        else if (a.getRating()-b.getRating() > 0) return -1;
        else return 0;
    }
}
