package tests;

import static org.junit.Assert.*;

import java.awt.image.*;
import org.junit.*;
import src.*;
import src.Media.Series;

public class SeriesTest {
    private Series s;
    @Before
    public void setup(){
        // String t = "Fun Series";
        // int r = 1995;
        // String[] run = new String[] {"it's a while bud"};
        // String[] gen = new String[] {"horror","drama"};
        // double rate = 6.9;
        // BufferedImage post = new BufferedImage(2,3,4);
        // int[] seas = new int[] {1,2,3,4}; 
        // this.s = new Series(t,r,run,gen,rate,post,seas);
        
        //it dont do
    }

    @Test
    public void seriesGetSeasonsIsCorrectLmao(){
        int[] expected = new int[] {4,3,5};

        int[] actual = s.getSeason();

        assertArrayEquals(expected, actual);
    }
}



