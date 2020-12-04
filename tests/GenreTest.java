package tests;

import java.awt.image.*;
import static org.junit.Assert.*;
import org.junit.*;
import src.*;
import src.Media.*;

public class GenreTest {
    private Genre g;
    private Movie m;
    private Series s;
    @Before
    public void setup(){
        this.g = new Genre("hewwo");
        this.m = new Movie("Movie", 2000, new String[] {"horror"}, 6.9, new BufferedImage(2, 3, 4));
        this.s = new Series("Series", 1996, "A Lot", new String[] {"thriller"}, 5.4, new BufferedImage(2,3,4), new int[] {1});
        g.add(m);
        g.add(s);
    }

    @Test
    public void gName_IsHewwo(){
        String expected = "hewwo";
        String actual = g.getName();
        assertEquals(expected,actual);
    }
    
    @Test
    public void gGetMediaSize(){
        int expected = 2;
        int actual = g.getMedia().size();
        assertEquals(expected,actual);
    }

    @Test
    public void gGetMovieSize(){
        int expected = 1;
        int actual = g.getMovies().size();
        assertEquals(expected, actual);
    }

    @Test
    public void getSeriesSize(){
        int expected = 1;
        int actual = g.getSeries().size();
        assertEquals(expected,actual);
    }

    @Test
    public void gAddMedia(){
        Movie smile = new Movie("Wahoo", 420, new String[] {"comedy"}, 6.9, new BufferedImage(2, 3, 4));
        Movie hehe = new Movie("Oh No", 2008, new String[] {"horror"}, 6.9, new BufferedImage(2, 3, 4));
        g.add(smile);
        g.add(hehe);

        Assert.assertTrue(g.getMedia().contains(smile));
        Assert.assertTrue(g.getMedia().contains(hehe));
    }

    @After
    public void tearDown() {
        g = null;
    }
}
