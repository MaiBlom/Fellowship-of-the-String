package tests;

import static org.junit.Assert.*;
import org.junit.*;
import src.*;

public class MediaDBtest {
    private MediaDB db;
    @Before
    public void setUp() {
        this.db = MediaDB.getInstance();
        MediaReader fr = MediaReader.getInstance(db);
        fr.readAll();
    }
    
    @Test
    public void dbSize_equals200() {
        //Arrange
        int expected = 200;
        
        //Act
        int actual = db.getAllMedia().size();
        
        //Assert
        assertEquals(expected, actual);
    }
    
    @After
    public void tearDown() {
        db = null;
    }
}