package tests;
import java.awt.image.*;
import static org.junit.Assert.*;
import org.junit.*;
import src.Media.*;
public class MovieTest {
    private Movie m;
    @Before
    public void setup(){
        this.m = new Movie("hehehe", 42069, new String[] {"comedy"}, 4.20, new BufferedImage(2,3,4));
    }
    
    @Test
    public void movieGetTitle(){
        String expected = "hehehe";

        String actual = m.getTitle();

        assertEquals(expected,actual);
    }

    @Test
    public void movieGetRelease(){

        
    }
}
