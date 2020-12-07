package tests;

import src.*;
import src.Media.*;
import static org.junit.Assert.*;
import java.awt.image.*;
import java.util.ArrayList;

import org.junit.*;

public class UserTest {
    private User user;

    @Before
    public void setup(){
        this.user = new User("Frodo");
    }

    @Test
    public void getUsername_returnsFrodo() {
        String expected = "Frodo";
        String actual = user.getUsername();
        assertEquals(expected, actual);
    }

    @Test
    public void getFavoriteMovies_returnsEmptySet() {
        int expected = 0;
        int actual = user.getFavoriteMovies().size();
        assertEquals(expected, actual);
    }

    @Test
    public void getFavoriteSeries_returnsEmptySet() {
        int expected = 0;
        int actual = user.getFavoriteSeries().size();
        assertEquals(expected, actual);
    }

    @Test
    public void getFavoriteMovies_returnsSet() {
        ArrayList<Media> expected = new ArrayList<>();
        Movie rotk = new Movie("Return of the King", 2002, new String[] {"Fantasy","Epic"}, 9.9, new BufferedImage(2,3,4));
        Movie fotr = new Movie("Fellowship of the Ring", 2000, new String[] {"Fantasy","Legendary"}, 9.8, new BufferedImage(2,3,4));
        expected.add(rotk);
        expected.add(fotr);
        user.favorite(rotk);
        user.favorite(fotr);
        ArrayList<Media> actual = user.getFavoriteMovies();

        assertEquals(expected.size(), actual.size());
        
        for (int i = 0; i<actual.size(); i++) {
            assertTrue(expected.contains(actual.get(i)));
        }
    }

    @Test
    public void getFavoriteSeries_returnsSet() {
        ArrayList<Media> expected = new ArrayList<>();
        Series rotk = new Series("Return of the King", 2002, "2002-2004", new String[] {"Fantasy","Epic"}, 9.9, new BufferedImage(2,3,4), new int[] {1,2,3});
        Series fotr = new Series("Fellowship of the Ring", 2000, "2000-2005", new String[] {"Fantasy","Legendary"}, 9.8, new BufferedImage(2,3,4), new int[] {1,2,3});
        expected.add(rotk);
        expected.add(fotr);
        user.favorite(rotk);
        user.favorite(fotr);
        ArrayList<Media> actual = user.getFavoriteSeries();

        assertEquals(expected.size(), actual.size());
        
        for (int i = 0; i<actual.size(); i++) {
            assertTrue(expected.contains(actual.get(i)));
        }
    }

    @Test
    public void isFavorite_returnsFalse() {
        Movie m = new Movie("hehehe", 42069, new String[] {"comedy","horror"}, 4.20, new BufferedImage(2,3,4));
        assertFalse(user.isFavorite(m));
    }

    @Test
    public void isFavorite_returnsTrue() {
        Movie m = new Movie("hehehe", 42069, new String[] {"comedy","horror"}, 4.20, new BufferedImage(2,3,4));
        user.favorite(m);
        assertTrue(user.isFavorite(m));
    }

    @Test
    public void unfavorite_works() {
        Movie m = new Movie("Two Towers", 2001, new String[] {"Fantasy","Amazing"}, 9.9, new BufferedImage(2,3,4));
        user.favorite(m);
        assertTrue(user.isFavorite(m));
        user.unfavorite(m);
        assertFalse(user.isFavorite(m));
    }

    @Test
    public void getRating_returns5() {
        Movie m = new Movie("Two Towers", 2001, new String[] {"Fantasy","Amazing"}, 9.9, new BufferedImage(2,3,4));
        user.rate(m, 5.0);

        double expected = 5.0;
        double actual = user.getRating(m);
        assertEquals(expected, actual, 0.001);
    }

    @After
    public void tearDown(){
        user = null;
    }
}
