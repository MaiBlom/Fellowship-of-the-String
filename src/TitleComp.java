package src;
import java.util.Comparator;

/* 
 * This class is used for comparing to Media objects
 * when sorting collections by title. This comparator 
 * will sort media in alphabetical order.
 */ 

public class TitleComp implements Comparator<Media> {
    public int compare(Media a, Media b) {
        return a.getTitle().compareTo(b.getTitle());
    }
}
