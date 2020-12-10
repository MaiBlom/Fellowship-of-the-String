import static org.junit.Assert.*;

import java.awt.image.*;
import org.junit.*;
import Media.*;

public class SeriesTest {
    private Series s;
    @Before
    public void setup(){
        this.s = new Series("Fun Series", 1995, "1995-2000", new String[] {"horror","drama"}, 
                            6.9, new BufferedImage(2,3,4), new int[] {1,2,3,4});
    }

    @Test
    public void getSeasonsIsCorrectLmao(){
        int[] expected = new int[] {1,2,3,4};
        int[] actual = s.getSeason();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void getRuntimeIsCorrectLmao() {
        String expected = "1995-2000";
        String actual = s.getRuntime();
        assertEquals(expected, actual);
    }

    @Test
    public void getTitleIsCorrect() {
        String expected = "Fun Series";
        String actual = s.getTitle();
        assertEquals(expected, actual);
    }

    @Test
    public void getReleaseIsCorrect() {
        int expected = 1995;
        int actual = s.getRelease();
        assertEquals(expected, actual);
    }

    @Test
    public void getGenresIsCorrect() {
        String[] expected = new String[] {"horror","drama"};
        String[] actual = s.getGenres();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void getRatingIsCorrect() {
        double expected = 6.9;
        double actual = s.getRating();
        assertEquals(expected, actual, 0.001);
    }

    @Test
    public void getPosterReturnsImage() {
        // Don't know how to check if two objects are the same (not completely equals).
        assertNotNull(s.getPoster());
    }

    @After
    public void tearDown(){
        s = null;
    }
}