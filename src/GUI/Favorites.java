package src.GUI;

import src.User;
import src.Comparators.RatingComp;
import src.Comparators.ReleaseComp;
import src.Comparators.TitleComp;
import src.Media.Media;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Favorites extends JLayeredPane implements Clickable {
    private static final long serialVersionUID = 1L;
    private Container contentPane;
    private Container topMenu;
    private Container mediaContainer;
    private Container favoriteMovieContainer;
    private Container favoriteSeriesContainer;
    private JPanel favoriteSeriesPanel;
    private JPanel favoriteMoviesPanel;
    private JLabel favoritesLabel;
    private JLabel resultsLabel;
    private User currentUser;
    private ArrayList<JButton> allResultButtons;
    private int numberOfResults;
    private GUI origin;

    private int WIDTH = 1000;
    private int HEIGHT = 700;


    public Favorites(User currentUser, GUI origin) {
        this.currentUser = currentUser;
        this.origin = origin;
        allResultButtons = new ArrayList<>(); //A list to keep track of all the JButtons so that we can operate with them later on.
        setup();
    }

    //Sets up the Favorites window by calling all the makeContainer methods.
    public void setup() {
        contentPane = new Container();
        contentPane.setLayout(new BorderLayout());
        this.add(contentPane, new Integer(-1));
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        contentPane.setBounds(0,0,WIDTH,HEIGHT);

        countResults();
        makeLabelContainer();
        makeMediaContainer();
        makeFavoriteMoviesContainer();
        makeFavoriteSeriesContainer();
        makeSorting();
    }

    //Makes the label container that holds just the small title bar stating that we're viewing the favorites window,
    //as well as displaying the total number of favorited media that the current user has.
    public void makeLabelContainer() {
        topMenu = new Container();
        topMenu.setLayout(new GridLayout(1,3));

        favoritesLabel = new JLabel("Favorites");
        resultsLabel = new JLabel("Total Results: " + numberOfResults);

        topMenu.add(favoritesLabel);
        topMenu.add(resultsLabel);

        contentPane.add(topMenu,BorderLayout.NORTH);
    }

    public void countResults() {
        numberOfResults = currentUser.getFavoriteMovies().size()+currentUser.getFavoriteSeries().size();
    }

    //Creates the container that will hold the subcontainers "favoriteMovieContainer" and "favoriteSeriesContainer"
    //and adds this container to the contentPane's BorderLayout in the center.
    public void makeMediaContainer() {
        mediaContainer = new Container();
        mediaContainer.setLayout(new GridLayout(2,1));

        contentPane.add(mediaContainer,BorderLayout.CENTER);
    }


    public void makeMovieButtons() {
        ArrayList<Media> movies = currentUser.getFavoriteMovies();

        for (Media m : movies) {                                               
            JButton mediaPoster = new JButton(new ImageIcon(m.getPoster()));   
            allResultButtons.add(mediaPoster);                                 
            mediaPoster.addActionListener(l -> showMediaInfo(m));              
            favoriteMoviesPanel.add(mediaPoster);                              
            }
    }

    public void makeSeriesButtons() {
        ArrayList<Media> series = currentUser.getFavoriteSeries();

        for (Media m : series) {                                               
            JButton mediaPoster = new JButton(new ImageIcon(m.getPoster()));   
            allResultButtons.add(mediaPoster);                                 
            mediaPoster.addActionListener(l -> showMediaInfo(m));              
            favoriteSeriesPanel.add(mediaPoster);                              
        }
    }

    //Creates a container with a BorderLayout to display both the JLabel "My favorite movies:", as well as all
    //the JButtons that we add our images to.
    public void makeFavoriteMoviesContainer() {
        favoriteMovieContainer = new Container();
        favoriteMovieContainer.setLayout(new BorderLayout());

        //The panel that will hold all the JButtons with the accompanying images.
        //Uses a FlowLayout for the scalability properties.
        favoriteMoviesPanel = new JPanel();
        favoriteMoviesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        //The label added just above the movie images to denote that the below images are a list of movies.
        JLabel favoriteMovieLabel = new JLabel("My Favorite Movies: ");
        favoriteMovieContainer.add(favoriteMovieLabel,BorderLayout.NORTH);

        makeMovieButtons();
                                                                          
        favoriteMovieContainer.add(favoriteMoviesPanel,BorderLayout.CENTER);
        mediaContainer.add(favoriteMovieContainer);

    }

    //Creates a container with a BorderLayout to display both the JLabel "My favorite series:", as well as all
    //the JButtons that we add our images to.
    public void makeFavoriteSeriesContainer() {
        favoriteSeriesContainer = new Container();
        favoriteSeriesContainer.setLayout(new BorderLayout());

        //The panel that will hold all the JButtons with the accompanying images.
        //Uses a FlowLayout for the scalability properties.
        favoriteSeriesPanel = new JPanel();
        favoriteSeriesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel favoriteSeriesLabel = new JLabel("My Favorite Series: ");
        favoriteSeriesContainer.add(favoriteSeriesLabel,BorderLayout.NORTH);

        makeSeriesButtons();

        favoriteSeriesContainer.add(favoriteSeriesPanel,BorderLayout.CENTER);
        mediaContainer.add(favoriteSeriesContainer);
    }

    private void showMediaInfo(Media m) {
        MediaInfoWindow info = new MediaInfoWindow(m,origin,currentUser);
        add(info, new Integer(1));
        info.setLocation(WIDTH/2-info.getWidth()/2, HEIGHT/2-info.getWidth()/2);
        info.setVisible(true);
        info.show();
    }

    private void makeSorting() {
        Container sortingContainer = new Container();
        sortingContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));

        String[] sortingOptions = {"Sort by...", "Title", "Release", "Rating"};
        JComboBox<String> sortby = new JComboBox<>(sortingOptions);
        sortby.addActionListener(e -> sort((String) sortby.getSelectedItem()));
        sortingContainer.add(sortby);

        topMenu.add(sortingContainer);
    }

    private void sort(String s) {
        if (s.equals("Title")) {
            Collections.sort(currentUser.getFavoriteMovies(), new TitleComp());
            Collections.sort(currentUser.getFavoriteSeries(), new TitleComp());
        } else if (s.equals("Release")) {
            Collections.sort(currentUser.getFavoriteMovies(), new ReleaseComp());
            Collections.sort(currentUser.getFavoriteSeries(), new ReleaseComp());
        } else if (s.equals("Rating")) {
            Collections.sort(currentUser.getFavoriteMovies(), new RatingComp());
            Collections.sort(currentUser.getFavoriteSeries(), new RatingComp());
        }
        favoriteMoviesPanel.removeAll();
        favoriteSeriesPanel.removeAll();
        allResultButtons.clear();
        makeMovieButtons();   
        makeSeriesButtons();
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        contentPane.setBounds(0,0,WIDTH,HEIGHT);
        origin.pack();
    }

    public void buttonsSetEnabled(boolean b) {
        for (JButton x : allResultButtons) {
            x.setEnabled(b);
        }
    }
}   