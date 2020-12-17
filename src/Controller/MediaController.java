package Controller;

import Comparators.*;
import Exceptions.InvalidPasswordException;
import Exceptions.InvalidUsernameException;
import Exceptions.UsernameTakenException;
import Model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MediaController {
    private static MediaController instance;
    private MediaDB mDB;
    private UserDB uDB;

    private MediaController() {
        mDB = MediaDB.getInstance();
        uDB = UserDB.getInstance();
        this.setupStartingUsers();
    }

    public static MediaController getInstance() {
        if (instance == null) instance = new MediaController();
        return instance;
    }

    // The results for the search are found by checking if there has been searched for movies or series specifically.
    // If both movies and series are selected, or if none is selected, both types are shown in the search.
    // The media is taken from the media database and stored in a temporary arraylist. This arraylist is then iterated
    // over and all media that fit the search criteria is put into the results-arraylist.
    // The results will only include media that fit all search criteria.
    private static String[] selectableGenres = new String[] {
            "Crime", "Drama", "Biography", "Sport", "History",
            "Romance", "War", "Mystery", "Adventure", "Family",
            "Fantasy", "Thriller", "Horror", "Film-Noir", "Action",
            "Sci-fi", "Comedy", "Musical", "Western", "Music", "Talk-show",
            "Documentary", "Animation"};
    public String[] getSelectableGenres() { return selectableGenres; }
    public ArrayList<Media> search(String textSearch, boolean searchMovies, boolean searchSeries, boolean[] searchGenres) {
        ArrayList<Media> searchResults = new ArrayList<>();
        ArrayList<Media> temp;
        if ((searchMovies && searchSeries) || (!searchMovies && !searchSeries)) temp = mDB.getAllMedia();
        else if (searchMovies) temp = mDB.getMovies();
        else temp = mDB.getSeries();

        // Next media-object in the iterator is initialized.
        for (Media m : temp) {
            boolean include = true;                                                 // We're using the intersection-method of the search. To switch to the union-method, negate the boolean statements.
            if (m.getTitle().toLowerCase().contains(textSearch.toLowerCase())) {    // Start out by checking if the title of the media contains the search string (casing doesn't matter).
                String genres = Arrays.toString(m.getGenres());                     // Convert the genre-array of the media to a string so that we can call contains() on it.
                for (int i = 0; i < searchGenres.length; i++) {                     // Go through all values in the searchGenres array to find which genres we're searching for
                    if (searchGenres[i]) {                                          // If the value is true, check whether or not the genre-string contains the corresponding genre-title.
                        if (!genres.contains(selectableGenres[i]))
                            include = false;                                        // If it doesn't, then don't include it. To switch to the union-method, change it to:x
                    }                                                               // if (genres.contains(selectableGenres[i])) include = true
                }
            } else include = false;

            if (include) searchResults.add(m);
        }
        makeSearchLabel(textSearch, searchMovies, searchSeries, searchGenres, searchResults.size());
        return searchResults;
    }
    private String curSearchLabel;
    public String getCurSearchLabel() { return curSearchLabel; }
    private void makeSearchLabel(String textSearch, boolean searchMovies, boolean searchSeries, boolean[] searchGenres, int numberOfResults) {
        StringBuilder sb = new StringBuilder("<html>You have searched for " +
                (((searchMovies && searchSeries) || (!searchMovies && !searchSeries))? "movies and series." : "") +
                ((searchMovies && !searchSeries)? "movies." : "") + ((!searchMovies && searchSeries)? "series." : ""));

        boolean firstGenre = true;
        for (int i = 0; i<selectableGenres.length; i++) {
            if (searchGenres[i]) {
                if (firstGenre) {
                    sb.append("<br>Genres: ");
                }
                if (firstGenre) firstGenre = false;
                else sb.append(", ");
                sb.append(selectableGenres[i]);
            }
        }

        sb.append("<br>Results: ");
        sb.append(numberOfResults);
        curSearchLabel = sb.toString();
    }

    // Sorting media moved to Controller
    private static String[] sortingOptions = {"Sort by...", "Title (A-Z)", "Title (Z-A)", "Release (newest to oldest)", "Release (oldest to newest)", "Rating (highest to lowest)", "Rating (lowest to highest)"};
    public String[] getSortingOptions() { return sortingOptions; }
    public ArrayList<Media> sort(String sortBy, ArrayList<Media> mediaToSort) {
        switch (sortBy) {
            case "Title (A-Z)":
                mediaToSort.sort(new TitleCompAZ());
                break;
            case "Title (Z-A)":
                mediaToSort.sort(new TitleCompZA());
                break;
            case "Release (newest to oldest)":
                mediaToSort.sort(new ReleaseCompDecreasing());
                break;
            case "Release (oldest to newest)":
                mediaToSort.sort(new ReleaseCompIncreasing());
                break;
            case "Rating (highest to lowest)":
                mediaToSort.sort(new RatingCompDecreasing());
                break;
            case "Rating (lowest to highest)":
                mediaToSort.sort(new RatingCompIncreasing());
                break;
        }
        return mediaToSort;
    }

    private User currentUser;
    public void setCurrentUser(User currentUser) { this.currentUser = currentUser; }
    public User getCurrentUser() { return currentUser; }

    // MediaDB getters.
    public ArrayList<Media> getAllMedia() { return mDB.getAllMedia(); }
    public ArrayList<Media> getMovies() { return mDB.getMovies(); }
    public ArrayList<Media> getSeries() { return mDB.getSeries(); }
    public ArrayList<Media> getRecommended() { return mDB.getRecommended(); }

    // UserDB methods
    public boolean login(String username, char[] password) throws InvalidUsernameException, InvalidPasswordException {
        HashMap<String, User> users = uDB.getUsers();
        if (!users.containsKey(username)) throw new InvalidUsernameException(username);
        else if (!Arrays.equals(users.get(username).getEncryptedPassword(), uDB.encryptPassword(password))) throw new InvalidPasswordException();
        else return true;
    }
    public void createUser(String username, char[] password) throws UsernameTakenException {
        HashMap<String, User> users = uDB.getUsers();
        if (username.length() == 0) throw new IllegalArgumentException("Username must be of at least length 1.");
        else if (password.length == 0) throw new IllegalArgumentException("Password must be of at least length 1.");
        else if (users.containsKey(username)) throw new UsernameTakenException(username);
        else users.put(username, new User(username, uDB.encryptPassword(password)));
    }
    public User getUser(String username) {
        HashMap<String, User> users = uDB.getUsers();
        return users.get(username);
    }

    private void setupStartingUsers() {
        ArrayList<Media> movies = mDB.getMovies();
        ArrayList<Media> series = mDB.getSeries();

        createUser("test", new char[] {'t','e','s','t'});
        User test = getUser("test");
        test.favorite(movies.get(1));
        test.favorite(movies.get(9));
        test.favorite(movies.get(63));
        test.favorite(series.get(24));
        test.favorite(series.get(55));
        test.favorite(series.get(73));
    }
}
