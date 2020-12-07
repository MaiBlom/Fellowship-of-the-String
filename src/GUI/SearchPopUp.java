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

public class SearchPopUp extends JInternalFrame {
    private static final long serialVersionUID = 1L;
    private GUI origin;
    private User currentUser;

    private Container contentPane;
    private Container searchBarContainer;
    private Container centerContainer;
    private Container mediatypeContainer;
    private Container genresContainer;
    private Container bottomBar;
    private Container allGenres;

    // search criteria fields
    private JTextField searchBar;
    private boolean searchMovies;
    private boolean searchSeries;
    private boolean[] searchGenres;
    private String[] selectableGenres;
        // Crime, Drama, Biography, Sport, History, Romance, War, Mystery, Adventure
        // Family, Fantasy, Thriller, Horror, Film-Noir, Action, Sci-fi, Comedy
        // Musical, Western, Music, Talk-show, Documentary, Animation

    public SearchPopUp(GUI origin, User currentUser) {
        super("Search", false, true);
        this.origin = origin;
        this.currentUser = currentUser;
        setup();
        pack();
        setVisible(true);
    }

    // Setup of the contentpane that consists of three elements, the top bar with a search bar, 
    // the middle with check boxes, and the bottom with a cancel and a search button.
    private void setup() {
        contentPane = super.getContentPane();
        contentPane.setLayout(new BorderLayout());

        setupSearchBar();
        setupCenterContainer();
        setupBottomBar();
    }

    // Setup of the topbar with a free-text searchbar. 
    private void setupSearchBar() {
        searchBarContainer = new Container();
        searchBarContainer.setLayout(new GridLayout(2,1));
        contentPane.add(searchBarContainer, BorderLayout.NORTH);

        JLabel searchTitle = new JLabel("Search");
        searchBarContainer.add(searchTitle);

        searchBar = new JTextField();
        searchBarContainer.add(searchBar);
    }

    // Setup of the center container with a BorderLayout. This container contains checkboxes for media 
    // type at the top and checkboxes for genres in the center.
    private void setupCenterContainer() {
        centerContainer = new Container();
        centerContainer.setLayout(new BorderLayout());
        contentPane.add(centerContainer, BorderLayout.CENTER);

        setupMediatypeContainer();
        setupGenreContainer();
    }

    // Setup of the media type checkboxes. Toggling the checkboxes will change the value of the two booleans
    // searchMovies and searchSeries which are used as search criteria.
    private void setupMediatypeContainer() {
        mediatypeContainer = new Container();
        mediatypeContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
        centerContainer.add(mediatypeContainer, BorderLayout.NORTH);
        
        JCheckBox movieSearch = new JCheckBox("Movies  ");
        movieSearch.addItemListener(l -> searchMovies = l.getStateChange()==1 ? true : false);

        JCheckBox seriesSearch = new JCheckBox("Series  ");
        seriesSearch.addItemListener(l -> searchSeries = l.getStateChange()==1 ? true : false);

        mediatypeContainer.add(movieSearch);
        mediatypeContainer.add(seriesSearch);
    }

    // The following two methods are the setup of the genre checkboxes. Toggling the checkboxes 
    // will change the value of the booleans in the array searchGenres which are used as search criteria.
    private void setupGenreContainer() {
        genresContainer = new Container();
        genresContainer.setLayout(new BorderLayout());
        centerContainer.add(genresContainer, BorderLayout.CENTER);

        JLabel genresTitle = new JLabel("Genres");
        genresContainer.add(genresTitle, BorderLayout.NORTH);

        setupGenres();
    }

    private void setupGenres() {
        allGenres = new Container();
        allGenres.setLayout(new GridLayout(1,3));
        genresContainer.add(allGenres, BorderLayout.CENTER);

        Container leftGenres = new Container();
        leftGenres.setLayout(new BoxLayout(leftGenres, BoxLayout.Y_AXIS));
        allGenres.add(leftGenres);
        Container centerGenres = new Container();
        centerGenres.setLayout(new BoxLayout(centerGenres, BoxLayout.Y_AXIS));
        allGenres.add(centerGenres);
        Container rightGenres = new Container();
        rightGenres.setLayout(new BoxLayout(rightGenres, BoxLayout.Y_AXIS));
        allGenres.add(rightGenres);

        searchGenres = new boolean[23];
        selectableGenres = new String[] {"Crime", "Drama", "Biography", "Sport", "History", "Romance", "War", "Mystery", "Adventure", 
                                         "Family", "Fantasy", "Thriller", "Horror", "Film-Noir", "Action", "Sci-fi", "Comedy",
                                         "Musical", "Western", "Music", "Talk-show", "Documentary", "Animation"};
        for (int i = 0; i<23; i++) {
            JCheckBox genreBox = new JCheckBox(selectableGenres[i]);
            genreBox.addItemListener(l -> updateGenreSearchCriteria(genreBox.getText(), (l.getStateChange()==1 ? true : false)));
            if (i<8) leftGenres.add(genreBox);
            else if (i<16) centerGenres.add(genreBox);
            else rightGenres.add(genreBox);
        }
    }

    // When a genre checkbox is toggled, this method is called. We'll loop through the selectableGenres array
    // and find the index of the genre that is toggled and then change the value in the searchGenres array on
    // the same index.
    private void updateGenreSearchCriteria(String genre, boolean state) {
        int index = 0;
        for (int i = 0; i<23; i++) {
            if (selectableGenres[i].equals(genre)) {
                index = i;
                break;
            }
        }
        searchGenres[index] = state;
    }

    // Setup of the bottom bar. The container has two buttons: One to cancel the search and one to search
    // with the given criteria. 
    private void setupBottomBar() {
        bottomBar = new Container();
        bottomBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        contentPane.add(bottomBar, BorderLayout.SOUTH);
        
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> dispose());
        bottomBar.add(cancel);

        JButton search = new JButton("Search");
        search.addActionListener(e -> clickSearch());
        bottomBar.add(search);
    }

    private void clickSearch() {
        origin.changeScenario(new SearchResult(searchBar.getText(), searchMovies, searchSeries, searchGenres, currentUser));
        dispose();

        // should refresh the page with and show all media that fits the given parameters. Parameters are stored in:
        // text search: searchCriteria
        // type search: searchMovies & searchSeries
        // genre search: searchGenres[]
    }
}
