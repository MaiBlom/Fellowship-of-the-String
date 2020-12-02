package tests;
import java.awt.image.*;
import static org.junit.Assert.*;
import org.junit.*;
import src.Media.*;
public class MovieTest {
    private Movie m;
    @Before
    public void setup(){
        this.m = new Movie("hehehe", 42069, new String[] {"comedy","horror"}, 4.20, new BufferedImage(2,3,4));
    }
    
    @Test
    public void movieGetTitle_isHehehe(){
        String expected = "hehehe";

        String actual = m.getTitle();

        assertEquals(expected,actual);
    }

    @Test
    public void movieGetRelease_is42069(){
        int expected = 42069;

        int actual = m.getRelease();

        assertEquals(expected,actual);
    }

    @Test
    public void movieGetGenres_isComedyHorror(){
        String[] expected = new String[] {"comedy","horror"};

        String[] actual = m.getGenres();

        assertArrayEquals(expected,actual);
    }

    @Test
    public void movieGetRating_is420(){
        double expected = 4.20;

        double actual = m.getRating();

        assertEquals(expected,actual,0);
    }

    @Test
    public void movieImage_ImageNotNull(){
        // BufferedImage expected = new BufferedImage(2, 3, 4);

        // BufferedImage actual = m.getPoster();
        //assertEquals(expected,actual);
        //NO CAN DO BROTHER... NO CAN DO
        assertNotNull(m.getPoster());
    }


    @After
    public void tearDown(){
        m = null;
    }
}
