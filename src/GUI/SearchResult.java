package src.GUI;

import src.*;
import src.Media.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.IOException;

public class SearchResult extends JLayeredPane {
    private MediaDB db;
    private Container contentPane;
    private JLabel yourSearch;
    // search criteria
    private String textSearch;
    private boolean searchMovies;
    private boolean searchSeries;
    private boolean[] searchGenres;
    private String[] selectableGenres;
        // Crime, Drama, Biography, Sport, History, Romance, War, Mystery, Adventure
        // Family, Fantasy, Thriller, Horror, Film-Noir, Action, Sci-fi, Comedy
        // Musical, Western, Music, Talk-show, Documentary, Animation
    private ArrayList<Media> results;

    private int WIDTH = 1000;
    private int HEIGHT = 700;
    
    // The search results obejct will be called from the SearchPopUp class with the given parameters.
    public SearchResult(String ts, boolean sm, boolean ss, boolean[] sg) {
        textSearch = ts;
        searchMovies = sm;
        searchSeries = ss;
        searchGenres = sg;
        selectableGenres = new String[] {"Crime", "Drama", "Biography", "Sport", "History", "Romance", "War", "Mystery", "Adventure", "Family", "Fantasy", "Thriller", "Horror", "Film-Noir", "Action", "Sci-fi", "Comedy", "Musical", "Western", "Music", "Talk-show", "Documentary", "Animation"};
        db = MediaDB.getInstance();
        setup();
    }

    // Setup of the main page, which contains of a small description of what the search criteria are
    // and the results.
    private void setup() {
        contentPane = new Container();
        contentPane.setLayout(new BorderLayout());
        this.add(contentPane, new Integer(-1));
        this.add(new JLabel("hej"), new Integer(0));
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        contentPane.setBounds(0,0,WIDTH,HEIGHT);
        makeSearchLabel();
        findResults();
        showResults();
    }

    // The search criteria are shown at the top of the window with this JLabel
    private void makeSearchLabel() {
        StringBuilder sb = new StringBuilder("<html>You have searched for " + 
        (((searchMovies && searchSeries) || (!searchMovies && !searchSeries))? "movies and series." : "") + 
        ((searchMovies && !searchSeries)? "movies." : "") + ((!searchMovies && searchSeries)? "series." : ""));

        boolean anyGenres = false;
        boolean firstGenre = true;
        for (int i = 0; i<selectableGenres.length; i++) {
            if (searchGenres[i] == true) {
                if (anyGenres == false) {
                    sb.append("<br>Genres: ");
                    anyGenres = true;
                }
                if (firstGenre) firstGenre = false;
                else sb.append(", ");
                sb.append(selectableGenres[i]);
            }
        }

        sb.append("<br>Results: ");
        yourSearch = new JLabel(sb.toString());
        contentPane.add(yourSearch, BorderLayout.NORTH);
    }

    // The results for the search are found by checking if there has been searched for movies or series specifically.
    // If both movies and series are selected, or if none is selected, both types are shown in the search.
    // The media is taken from the media database and stored in a temporary arraylist. This arraylist is then iterated
    // over and all media that fit the search criteria is put into the results-arraylist.
    // The results will only include media that fit all search criteria.
    private void findResults() {
        results = new ArrayList<>();
        ArrayList<Media> temp = new ArrayList<>();
        if ((searchMovies && searchSeries) || (!searchMovies && !searchSeries)) temp = db.getAllMedia();
        else if (searchMovies) temp = db.getMovies();
        else temp = db.getSeries();

        Iterator<Media> it = temp.iterator();
        while (it.hasNext()) {
            Media m = it.next();                                                    // Next media-object in the iterator is initialized.
            boolean include = true;                                                 // We're using the intersection-method of the search. To switch to the union-method, negate the boolean statements. 
            if (m.getTitle().toLowerCase().contains(textSearch.toLowerCase())) {    // Start out by checking if the title of the media contains the search string (casing doesn't matter).
                String genres = Arrays.toString(m.getGenres());                     // Convert the genre-array of the media to a string so that we can call contains() on it.
                for (int i = 0; i<searchGenres.length; i++) {                       // Go throgh all values in the searchGenres array to find which genres we're searching for
                    if (searchGenres[i]) {                                          // If the value is true, check whether or not the genre-string contains the corresponding genre-title.
                        if (!genres.contains(selectableGenres[i])) include = false; // If it doesn't, then don't include it. To switch to the union-method, change it to:x
                    }                                                               // if (genres.contains(selectableGenres[i])) include = true
                }
            } else include = false;
            
            if (include) results.add(m);
        }
        yourSearch.setText(yourSearch.getText()+results.size());
    }

    // The results-container has a FlowLayout to add all media in the results-arraylist. A JScrollPane makes sure that we're able
    // to scroll through the results if there are more than what is shown on screen. The images of the media are put on buttons
    // which each call the showMediaInfo() method.
    private void showResults() {
        JPanel allResults = new JPanel();
        allResults.setLayout(new FlowLayout(FlowLayout.LEFT));

        for (Media m : results) {
            JButton mediaPoster = new JButton(new ImageIcon(m.getPoster()));
            mediaPoster.addActionListener(l -> showMediaInfo(m));
            allResults.add(mediaPoster);
        }

        
        //JScrollPane allResultsScroll = new JScrollPane(allResults,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //contentPane.add(allResultsScroll);
        contentPane.add(allResults);
    }

    // Make a MediaInfoWindow popup with the given media.
    private void showMediaInfo(Media m) {
        MediaInfoWindow info = new MediaInfoWindow(m);
        add(info, new Integer(1));
        info.setLocation(WIDTH/2-info.getWidth()/2, HEIGHT/2-info.getWidth()/2);
        info.setVisible(true);
        info.show();
    }
}
