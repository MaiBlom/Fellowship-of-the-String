package tests;

import static org.junit.Assert.*;
import org.junit.*;
import src.*;

public class MediaDBtest {
    private MediaDB db;
    @Before
    public void setUp() {
        this.db = MediaDB.getInstance();
        MediaReader mr = MediaReader.getInstance(db);
        mr.readAll();
    }
    
    @Test
    public void dbSize_equals200() {
        int expected = 200;
        int actual = db.getAllMedia().size();
        assertEquals(expected, actual);
    }
    
    @After
    public void tearDown() {
        db = null;
    }
}