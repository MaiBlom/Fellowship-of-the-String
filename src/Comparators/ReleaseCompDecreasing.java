package src.Comparators;

import java.util.Comparator;
import src.Media.*;

/* 
 * This class is used for comparing to Media objects
 * when sorting collections by release year. This comparator 
 * will sort media newest to oldest.
 */ 

public class ReleaseCompDecreasing implements Comparator<Media> {
    public int compare(Media a, Media b) {
        if (a.getRelease()-b.getRelease() < 0) return 1;
        else if (a.getRelease()-b.getRelease() > 0) return -1;
        else return 0;
    }
}
