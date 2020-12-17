package Comparators;

import java.util.Comparator;
import Model.*;

/* 
 * This class is used for comparing to Media objects
 * when sorting collections by release year. This comparator 
 * will sort media newest to oldest.
 */ 

public class ReleaseCompDecreasing implements Comparator<Media> {
    public int compare(Media a, Media b) {
        return b.getRelease()-a.getRelease();
    }
}