import Model.*;

import static org.junit.Assert.*;

import org.junit.*;

public class UserDBTest {
    private UserDB db;
    @Before
    public void setUp() {
        this.db = UserDB.getInstance();
    }

    @Test
    public void getUsers_returnsEmptyHashMap() {
        int expected = 0;
        int actual = db.getUsers().size();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        db = null;
    }
}
