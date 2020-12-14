import static org.junit.Assert.*;

import Model.MediaDB;
import org.junit.*;

public class MediaDBtest {
    private MediaDB db;
    @Before
    public void setUp() {
        this.db = MediaDB.getInstance();
    }
    
    @Test
    public void dbSize_equals200() {
        int expected = 200;
        int actual = db.getAllMedia().size();
        assertEquals(expected, actual);
    }

    @Test
    public void moviesSize_equals100() {
        int expected = 100;
        int actual = db.getMovies().size();
        assertEquals(expected, actual);
    }

    @Test
    public void seriesSize_equals100() {
        int expected = 100;
        int actual = db.getSeries().size();
        assertEquals(expected, actual);
    }
    
    @After
    public void tearDown() {
        db = null;
    }
}