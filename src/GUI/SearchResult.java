package src.GUI;

import src.*;
import src.Comparators.*;
import src.Media.*;
import src.GUI.PopUps.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class SearchResult extends JLayeredPane implements Clickable {
    private static final long serialVersionUID = 1L;
    private MediaDB db;
    private GUI origin;

    private JPanel contentPane;
    private JPanel topbar;
    private JLabel yourSearch;
    private JPanel allResultsPanel;
    private ArrayList<JButton> allButtons;
    private User currentUser;
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

    // private final int WIDTH = 1000, HEIGHT = 720;
    
    // The search results obejct will be called from the SearchPopUp class with the given parameters.
    public SearchResult(String ts, boolean sm, boolean ss, boolean[] sg, User currentUser, GUI origin) {
        textSearch = ts;
        searchMovies = sm;
        searchSeries = ss;
        searchGenres = sg;
        selectableGenres = new String[] {"Crime", "Drama", "Biography", "Sport", "History", "Romance", "War", "Mystery", "Adventure", "Family", "Fantasy", "Thriller", "Horror", "Film-Noir", "Action", "Sci-fi", "Comedy", "Musical", "Western", "Music", "Talk-show", "Documentary", "Animation"};
        db = MediaDB.getInstance();
        allButtons = new ArrayList<>();
        this.currentUser = currentUser;
        this.origin = origin;
        setup();
    }

    // Setup of the main page, which contains of a small description of what the search criteria are
    // and the results.
    private void setup() {
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        ColorTheme.paintMainPanel(contentPane);
        this.add(contentPane, new Integer(0));
        setPreferredSize(new Dimension(origin.getwidth()-12,origin.getheight()-82));
        contentPane.setBounds(0,0,origin.getwidth()-12,origin.getheight()-82);
        makeTopbar();
        findResults();
        showResults();
    }

    private void makeTopbar() {
        topbar = new JPanel();
        ColorTheme.paintMainPanel(topbar);
        topbar.setLayout(new GridLayout());
        contentPane.add(topbar, BorderLayout.NORTH);

        makeSearchLabel();
        makeSorting();
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
        TextSettings.paintHeader(yourSearch);
        topbar.add(yourSearch);
    }

    private void makeSorting() {
        JPanel sortingContainer = new JPanel();
        ColorTheme.paintMainPanel(sortingContainer);
        sortingContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));

        String[] sortingOptions = {"Sort by...", "Title (A-Z)", "Title (Z-A)", "Release (newest to oldest)", 
                                   "Release (oldest to newest)", "Rating (highest to lowest)", "Rating (lowest to highest)"};
        JComboBox<String> sortby = new JComboBox<>(sortingOptions);
        sortby.setBackground(ColorTheme.mainColor);
        sortby.setForeground(ColorTheme.textColor);
        sortby.setFont(new Font("Verdana", Font.BOLD, 15));
        sortby.addActionListener(e -> sort((String) sortby.getSelectedItem()));
        sortingContainer.add(sortby);

        topbar.add(sortingContainer);
    }

    private void sort(String s) {
        if (s.equals("Title (A-Z)")) {
            Collections.sort(results, new TitleCompAZ());
        } else if (s.equals("Title (Z-A)")) {
            Collections.sort(results, new TitleCompZA());
        } else if (s.equals("Release (newest to oldest)")) {
            Collections.sort(results, new ReleaseCompDecreasing());
        } else if (s.equals("Release (oldest to newest)")) {
            Collections.sort(results, new ReleaseCompIncreasing());
        } else if (s.equals("Rating (highest to lowest)")) {
            Collections.sort(results, new RatingCompDecreasing());
        } else if (s.equals("Rating (lowest to highest)")) {
            Collections.sort(results, new RatingCompIncreasing());
        }
        allResultsPanel.removeAll();
        allButtons.clear();
        createButtons();
        setPreferredSize(new Dimension(origin.getwidth()-12,origin.getheight()-82));
        contentPane.setBounds(0,0,origin.getwidth()-12,origin.getheight()-82);
        origin.pack();
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
        allResultsPanel = new JPanel();
        ColorTheme.paintMainPanel(allResultsPanel);
        allResultsPanel.setLayout(new FlowLayout(FlowLayout.LEFT,15,0));

        createButtons();

        JScrollPane allResultsScrollPane = new JScrollPane(allResultsPanel);
        contentPane.add(allResultsScrollPane);
    }

    private void createButtons() {
        int numberOfResults = results.size();

        for (Media m : results) {
            JButton mediaPoster = new JButton(new ImageIcon(m.getPoster()));

            mediaPoster.setText(m.getTitle());
            mediaPoster.setVerticalTextPosition(SwingConstants.BOTTOM);
            mediaPoster.setHorizontalTextPosition(SwingConstants.CENTER);
            ColorTheme.paintMediaButton(mediaPoster);
            mediaPoster.setPreferredSize(new Dimension(150,250));

            allButtons.add(mediaPoster);
            mediaPoster.addActionListener(l -> MediaInfoWindow.showMediaInfo(m, origin, currentUser, this));
            allResultsPanel.add(mediaPoster);
        }

        allResultsPanel.setPreferredSize(new Dimension(6*150,(numberOfResults/6 + (numberOfResults % 6 == 0? 0:1))*250));
        origin.pack();
    }

    public void buttonsSetEnabled(boolean b) {
        for (JButton x : allButtons) {
            x.setEnabled(b);
        }
    }
}
