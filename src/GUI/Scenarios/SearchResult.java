package GUI.Scenarios;

import GUI.*;
import Media.*;
import Misc.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class SearchResult extends Scenario {
    private static final long serialVersionUID = 1L;
    private final MediaDB db;

    private JPanel topMenu;
    // search criteria
    private final String textSearch;
    private final boolean searchMovies;
    private final boolean searchSeries;
    private final boolean[] searchGenres;
    private final String[] selectableGenres = {"Crime", "Drama", "Biography", "Sport", "History", "Romance", "War", "Mystery", "Adventure", "Family", "Fantasy", "Thriller", "Horror", "Film-Noir", "Action", "Sci-fi", "Comedy", "Musical", "Western", "Music", "Talk-show", "Documentary", "Animation"};
    private ArrayList<Media> searchResults;
    
    // The search results object will be called from the SearchPopUp class with the given parameters.
    public SearchResult(GUI origin, User currentUser, String ts, boolean sm, boolean ss, boolean[] sg) {
        super(origin, currentUser);
        this.textSearch = ts;
        this.searchMovies = sm;
        this.searchSeries = ss;
        this.searchGenres = sg;
        this.db = MediaDB.getInstance();

        setup();
    }

    // Setup of the main page, which contains of a small description of what the search criteria are
    // and the results.
    private void setup() {
        contentPane.setLayout(new BorderLayout());

        findResults();
        makeTopMenu();
        makeMediaContainer(searchResults);
    }

    private void makeTopMenu() {
        topMenu = new JPanel();
        ColorTheme.paintMainPanel(topMenu);
        topMenu.setLayout(new GridLayout());
        contentPane.add(topMenu, BorderLayout.NORTH);

        makeSearchLabel();
        makeSorting(searchResults);
        topMenu.add(sortingContainer);
    }

    // The search criteria are shown at the top of the window with this JLabel
    private void makeSearchLabel() {
        StringBuilder sb = new StringBuilder("<html>You have searched for " + 
        (((searchMovies && searchSeries) || (!searchMovies && !searchSeries))? "movies and series." : "") + 
        ((searchMovies && !searchSeries)? "movies." : "") + ((!searchMovies && searchSeries)? "series." : ""));

        boolean anyGenres = false;
        boolean firstGenre = true;
        for (int i = 0; i<selectableGenres.length; i++) {
            if (searchGenres[i]) {
                if (anyGenres) {
                    sb.append("<br>Genres: ");
                    anyGenres = true;
                }
                if (firstGenre) firstGenre = false;
                else sb.append(", ");
                sb.append(selectableGenres[i]);
            }
        }

        sb.append("<br>Results: ");
        sb.append(searchResults.size());
        JLabel yourSearch = new JLabel(sb.toString());
        TextSettings.paintHeader(yourSearch);
        topMenu.add(yourSearch);
    }

    // The results for the search are found by checking if there has been searched for movies or series specifically.
    // If both movies and series are selected, or if none is selected, both types are shown in the search.
    // The media is taken from the media database and stored in a temporary arraylist. This arraylist is then iterated
    // over and all media that fit the search criteria is put into the results-arraylist.
    // The results will only include media that fit all search criteria.
    private void findResults() {
        searchResults = new ArrayList<>();
        ArrayList<Media> temp;
        if ((searchMovies && searchSeries) || (!searchMovies && !searchSeries)) temp = db.getAllMedia();
        else if (searchMovies) temp = db.getMovies();
        else temp = db.getSeries();

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
    }

}