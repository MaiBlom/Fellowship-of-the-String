package GUI;

import Comparators.*;
import GUI.PopUps.*;
import Media.*;
import Misc.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Favorites extends JLayeredPane implements Clickable {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel topMenu;
    private JPanel mediaContainer;
    private JPanel favoriteMovieContainer;
    private JPanel favoriteSeriesContainer;
    private JPanel favoriteSeriesPanel;
    private JPanel favoriteMoviesPanel;
    private JLabel favoritesLabel;
    private JLabel resultsLabel;
    private User currentUser;
    private ArrayList<JButton> allResultButtons;
    private int numberOfResults;
    private GUI origin;


    public Favorites(User currentUser, GUI origin) {
        this.currentUser = currentUser;
        this.origin = origin;
        allResultButtons = new ArrayList<>(); //A list to keep track of all the JButtons so that we can operate with them later on.
        setup();
    }

    //Sets up the Favorites window by calling all the makeContainer methods.
    private void setup() {
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        this.add(contentPane, new Integer(0));
        setPreferredSize(origin.getCenterDimension());
        contentPane.setBounds(origin.getCenterBounds());

        countResults();
        makeLabelContainer();
        makeMediaContainer();
        makeFavoriteMoviesContainer();
        makeFavoriteSeriesContainer();
        makeSorting();
    }

    //Makes the label JPanel that holds just the small title bar stating that we're viewing the favorites window,
    //as well as displaying the total number of favorited media that the current user has.
    private void makeLabelContainer() {
        topMenu = new JPanel();
        topMenu.setLayout(new GridLayout(1,3));

        favoritesLabel = new JLabel("Favorites");
        TextSettings.paintHeader(favoritesLabel);
        resultsLabel = new JLabel("Total Results: " + numberOfResults);
        TextSettings.paintHeader(resultsLabel);

        topMenu.add(favoritesLabel);
        topMenu.add(resultsLabel);
        ColorTheme.paintMainPanel(topMenu);

        contentPane.add(topMenu,BorderLayout.NORTH);
        
    }

    private void countResults() {
        numberOfResults = currentUser.getFavoriteMovies().size()+currentUser.getFavoriteSeries().size();
    }

    //Creates the JPanel that will hold the subcontainers "favoriteMovieContainer" and "favoriteSeriesContainer"
    //and adds this JPanel to the contentPane's BorderLayout in the center.
    private void makeMediaContainer() {
        mediaContainer = new JPanel();
        mediaContainer.setLayout(new GridLayout(2,1));
        //ColorTheme.paintMainPanel(mediaContainer);

        contentPane.add(mediaContainer,BorderLayout.CENTER);
    }


    private void makeMovieButtons() {
        ArrayList<Media> movies = currentUser.getFavoriteMovies();

        for (Media m : movies) {                                               
            JButton mediaPoster = new JButton(new ImageIcon(m.getPoster()));   
            allResultButtons.add(mediaPoster); 
            ColorTheme.paintMediaButton(mediaPoster);        
            mediaPoster.addActionListener(l -> MediaInfoWindow.showMediaInfo(m, origin, currentUser, this));              
            favoriteMoviesPanel.add(mediaPoster);                              
            }
    }

    private void makeSeriesButtons() {
        ArrayList<Media> series = currentUser.getFavoriteSeries();

        for (Media m : series) {                                               
            JButton mediaPoster = new JButton(new ImageIcon(m.getPoster()));   
            allResultButtons.add(mediaPoster);      
            ColorTheme.paintMediaButton(mediaPoster);                                                        
            mediaPoster.addActionListener(l -> MediaInfoWindow.showMediaInfo(m, origin, currentUser, this));              
            favoriteSeriesPanel.add(mediaPoster);                              
        }
    }

    //Creates a JPanel with a BorderLayout to display both the JLabel "My favorite movies:", as well as all
    //the JButtons that we add our images to.
    private void makeFavoriteMoviesContainer() {
        favoriteMovieContainer = new JPanel();
        favoriteMovieContainer.setLayout(new BorderLayout());
        ColorTheme.paintMainPanel(favoriteMovieContainer);

        //The panel that will hold all the JButtons with the accompanying images.
        //Uses a FlowLayout for the scalability properties.
        favoriteMoviesPanel = new JPanel();
        favoriteMoviesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        ColorTheme.paintMainPanel(favoriteMoviesPanel);

        //The label added just above the movie images to denote that the below images are a list of movies.
        JLabel favoriteMovieLabel = new JLabel("My Favorite Movies: ");
        TextSettings.paintHeader(favoriteMovieLabel);
        favoriteMovieContainer.add(favoriteMovieLabel,BorderLayout.NORTH);

        makeMovieButtons();
                                                                          
        favoriteMovieContainer.add(favoriteMoviesPanel,BorderLayout.CENTER);
        mediaContainer.add(favoriteMovieContainer);

    }

    //Creates a JPanel with a BorderLayout to display both the JLabel "My favorite series:", as well as all
    //the JButtons that we add our images to.
    private void makeFavoriteSeriesContainer() {
        favoriteSeriesContainer = new JPanel();
        favoriteSeriesContainer.setLayout(new BorderLayout());
        ColorTheme.paintMainPanel(favoriteSeriesContainer);

        //The panel that will hold all the JButtons with the accompanying images.
        //Uses a FlowLayout for the scalability properties.
        favoriteSeriesPanel = new JPanel();
        favoriteSeriesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        ColorTheme.paintMainPanel(favoriteSeriesPanel);

        JLabel favoriteSeriesLabel = new JLabel("My Favorite Series: ");
        TextSettings.paintHeader(favoriteSeriesLabel);
        favoriteSeriesContainer.add(favoriteSeriesLabel,BorderLayout.NORTH);

        makeSeriesButtons();

        favoriteSeriesContainer.add(favoriteSeriesPanel,BorderLayout.CENTER);
        mediaContainer.add(favoriteSeriesContainer);
    }

    private void makeSorting() {
        JPanel sortingContainer = new JPanel();
        sortingContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));
        ColorTheme.paintMainPanel(sortingContainer);

        String[] sortingOptions = {"Sort by...", "Title (A-Z)", "Title (Z-A)", "Release (newest to oldest)", 
                                   "Release (oldest to newest)", "Rating (highest to lowest)", "Rating (lowest to highest)"};
        JComboBox<String> sortby = new JComboBox<>(sortingOptions);
        sortby.addActionListener(e -> sort((String) sortby.getSelectedItem()));
        sortby.setBackground(ColorTheme.mainColor);
        sortby.setForeground(ColorTheme.textColor);
        sortby.setFont(new Font("Verdana", Font.BOLD, 15));
        sortingContainer.add(sortby);


        topMenu.add(sortingContainer);
    }

    private void sort(String s) {
        if (s.equals("Title (A-Z)")) {
            Collections.sort(currentUser.getFavoriteMovies(), new TitleCompAZ());
            Collections.sort(currentUser.getFavoriteSeries(), new TitleCompAZ());
        } else if (s.equals("Release (newest to oldest)")) {
            Collections.sort(currentUser.getFavoriteMovies(), new ReleaseCompDecreasing());
            Collections.sort(currentUser.getFavoriteSeries(), new ReleaseCompDecreasing());
        } else if (s.equals("Rating (highest to lowest)")) {
            Collections.sort(currentUser.getFavoriteMovies(), new RatingCompDecreasing());
            Collections.sort(currentUser.getFavoriteSeries(), new RatingCompDecreasing());
        } else if (s.equals("Title (Z-A)")) {
            Collections.sort(currentUser.getFavoriteMovies(), new TitleCompZA());
            Collections.sort(currentUser.getFavoriteSeries(), new TitleCompZA());
        } else if (s.equals("Release (oldest to newest)")) {
            Collections.sort(currentUser.getFavoriteMovies(), new ReleaseCompIncreasing());
            Collections.sort(currentUser.getFavoriteSeries(), new ReleaseCompIncreasing());
        } else if (s.equals("Rating (lowest to highest)")) {
            Collections.sort(currentUser.getFavoriteMovies(), new RatingCompIncreasing());
            Collections.sort(currentUser.getFavoriteSeries(), new RatingCompIncreasing());
        }
        favoriteMoviesPanel.removeAll();
        favoriteSeriesPanel.removeAll();
        allResultButtons.clear();
        makeMovieButtons();   
        makeSeriesButtons();
        setPreferredSize(origin.getCenterDimension());
        contentPane.setBounds(origin.getCenterBounds());
        origin.pack();
    }

    public void buttonsSetEnabled(boolean b) {
        for (JButton x : allResultButtons) {
            x.setEnabled(b);
        }
    }
}