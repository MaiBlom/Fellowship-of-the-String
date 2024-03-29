package Comparators;

import java.util.Comparator;
import Model.*;

/* 
 * This class is used for comparing to Media objects
 * when sorting collections by release year. This comparator 
 * will sort media newest to oldest.
 */ 

public class ReleaseCompIncreasing implements Comparator<Media> {
    public int compare(Media a, Media b) {
        return a.getRelease()-b.getRelease();
    }
}