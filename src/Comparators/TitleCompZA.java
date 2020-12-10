package Comparators;

import java.util.Comparator;
import Media.*;

/* 
 * This class is used for comparing to Media objects
 * when sorting collections by title. This comparator 
 * will sort media in alphabetical order.
 */ 

public class TitleCompZA implements Comparator<Media> {
    public int compare(Media a, Media b) {
        return b.getTitle().compareTo(a.getTitle());
    }
}