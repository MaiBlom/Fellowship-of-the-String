package src.GUI.PopUps;

import src.*;
import src.GUI.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

public class SearchPopUp extends JInternalFrame {
    private static final long serialVersionUID = 1L;
    private GUI origin;
    private User currentUser;

    private Container contentPane;
    private JPanel searchBarContainer;
    private JPanel centerContainer;
    private JPanel mediatypeContainer;
    private JPanel genresContainer;
    private JPanel bottomBar;
    private JPanel allGenres;

    // search criteria fields
    private JTextField searchBar;
    private boolean searchMovies;
    private boolean searchSeries;
    private boolean[] searchGenres;
    private String[] selectableGenres;
        // Crime, Drama, Biography, Sport, History, Romance, War, Mystery, Adventure
        // Family, Fantasy, Thriller, Horror, Film-Noir, Action, Sci-fi, Comedy
        // Musical, Western, Music, Talk-show, Documentary, Animation

    public SearchPopUp(User currentUser, GUI origin) {
        super("Search", false, true);
        this.origin = origin;
        this.currentUser = currentUser;
        setup();
        setPreferredSize(new Dimension(400,400));
        pack();
        setVisible(true);
    }

    // Setup of the contentpane that consists of three elements, the top bar with a search bar, 
    // the middle with check boxes, and the bottom with a cancel and a search button.
    private void setup() {
        contentPane = super.getContentPane();
        contentPane.setLayout(new BorderLayout());

        setupWindowListener();
        setupSearchBar();
        setupCenterContainer();
        setupBottomBar();
    }

    // This looks like shit, but it makes sure, that buttons are disabled when
    // the window is opened, and enabled when the window is closed.
    private void setupWindowListener() {
        this.addInternalFrameListener(new InternalFrameListener() {
            public void internalFrameClosed(InternalFrameEvent e) {
                if (origin instanceof Clickable) {
                    Clickable clickable = (Clickable) origin;
                    clickable.buttonsSetEnabled(true);
                }
            }
            public void internalFrameOpened(InternalFrameEvent e) {
                if (origin instanceof Clickable) {
                    Clickable sr = (Clickable) origin;
                    sr.buttonsSetEnabled(false);
                }
            }    
            public void internalFrameClosing(InternalFrameEvent e) {}
            public void internalFrameIconified(InternalFrameEvent e) {}
            public void internalFrameDeiconified(InternalFrameEvent e) {}
            public void internalFrameActivated(InternalFrameEvent e) {}
            public void internalFrameDeactivated(InternalFrameEvent e) {}
    
        });
    }

    // Setup of the topbar with a free-text searchbar. 
    private void setupSearchBar() {
        searchBarContainer = new JPanel();
        searchBarContainer.setLayout(new GridLayout(2,1));
        contentPane.add(searchBarContainer, BorderLayout.NORTH);

        JLabel searchTitle = new JLabel("Search");
        searchBarContainer.add(searchTitle);

        searchBar = new JTextField();
        searchBarContainer.add(searchBar);
    }

    // Setup of the center JPanel with a BorderLayout. This JPanel contains checkboxes for media 
    // type at the top and checkboxes for genres in the center.
    private void setupCenterContainer() {
        centerContainer = new JPanel();
        centerContainer.setLayout(new BorderLayout());
        contentPane.add(centerContainer, BorderLayout.CENTER);

        setupMediatypeContainer();
        setupGenreContainer();
    }

    // Setup of the media type checkboxes. Toggling the checkboxes will change the value of the two booleans
    // searchMovies and searchSeries which are used as search criteria.
    private void setupMediatypeContainer() {
        mediatypeContainer = new JPanel();
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
        genresContainer = new JPanel();
        genresContainer.setLayout(new BorderLayout());
        centerContainer.add(genresContainer, BorderLayout.CENTER);

        JLabel genresTitle = new JLabel("Genres");
        genresContainer.add(genresTitle, BorderLayout.NORTH);

        setupGenres();
    }

    private void setupGenres() {
        allGenres = new JPanel();
        allGenres.setLayout(new GridLayout(1,3));
        genresContainer.add(allGenres, BorderLayout.CENTER);

        JPanel leftGenres = new JPanel();
        leftGenres.setLayout(new BoxLayout(leftGenres, BoxLayout.Y_AXIS));
        allGenres.add(leftGenres);
        JPanel centerGenres = new JPanel();
        centerGenres.setLayout(new BoxLayout(centerGenres, BoxLayout.Y_AXIS));
        allGenres.add(centerGenres);
        JPanel rightGenres = new JPanel();
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

    // Setup of the bottom bar. The JPanel has two buttons: One to cancel the search and one to search
    // with the given criteria. 
    private void setupBottomBar() {
        bottomBar = new JPanel();
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
        origin.changeScenario(new SearchResult(searchBar.getText(), searchMovies, searchSeries, searchGenres, currentUser, origin));
        dispose();

        // should refresh the page with and show all media that fits the given parameters. Parameters are stored in:
        // text search: searchCriteria
        // type search: searchMovies & searchSeries
        // genre search: searchGenres[]
    }
}
