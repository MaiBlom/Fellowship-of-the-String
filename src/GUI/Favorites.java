package src.GUI;

import src.User;
import src.Media.Media;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Favorites extends JFrame {
    private Container contentPane;
    private Container favoritesContainer;
    private Container mediaContainer;
    private Container favoriteMovieContainer;
    private Container favoriteSeriesContainer;
    private JLabel favoritesLabel;
    private JLabel resultsLabel;
    private User currentUser;

public static void main(String[] args) {
    new Favorites(new User("Frodo"));
}
    public Favorites(User currentUser) {
        this.currentUser = currentUser;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setup();
        pack();
        setVisible(true);
    }

    public void setup() {
        contentPane = new Container();
        contentPane.setLayout(new BorderLayout());

        makeLabelContainer();
        makeMediaContainer();
    }

    public void makeLabelContainer() {
        favoritesContainer = new Container();
        favoritesContainer.setLayout(new GridLayout(1,2));

        favoritesLabel = new JLabel("My Favorites: ");
        resultsLabel = new JLabel("Total Results: ");

        favoritesContainer.add(favoritesLabel);
        favoritesContainer.add(resultsLabel);

        contentPane.add(favoritesContainer,BorderLayout.NORTH);
    }

    public void makeMediaContainer() {
        mediaContainer = new Container();
        mediaContainer.setLayout(new GridLayout(2,1));

        contentPane.add(mediaContainer,BorderLayout.CENTER);
    }

    public void makeFavoriteMoviesContainer() {
        favoriteMovieContainer = new Container();
        favoriteMovieContainer.setLayout(new BorderLayout());

        JPanel favoriteMoviesPanel = new JPanel();
        favoriteMoviesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel favoriteMovieLabel = new JLabel("My Favorite Movies: ");
        favoriteMovieContainer.add(favoriteMovieLabel,BorderLayout.NORTH);

        ArrayList<Media> movies = currentUser.getFavoriteMovies();

        for (Media m : movies) {
            //if (m instanceof Movie){  Doesn't need the if statement because the list is already getFavoriteMovies
                JButton mediaPoster = new JButton(new ImageIcon(m.getPoster()));
                //mediaPoster.addActionListener(l -> showMediaInfo(m));   Temporarily commented until I can get showMediaInfo working
                favoriteMoviesPanel.add(mediaPoster);
           // }
        }
        favoriteMovieContainer.add(favoriteMoviesPanel,BorderLayout.CENTER);
        mediaContainer.add(favoriteMovieContainer);

    }

    public void makeFavoriteSeriesContainer() {
        favoriteSeriesContainer = new Container();
        favoriteSeriesContainer.setLayout(new BorderLayout());

        JPanel favoriteSeriesPanel = new JPanel();
        favoriteSeriesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel favoriteSeriesLabel = new JLabel("My Favorite Series: ");
        favoriteSeriesContainer.add(favoriteSeriesLabel,BorderLayout.NORTH);

        ArrayList<Media> series = currentUser.getFavoriteSeries();

        for (Media m : series) {
            //if (m instanceof Series){  Doesn't need the if statement because the list is already getFavoriteSeries
                JButton mediaPoster = new JButton(new ImageIcon(m.getPoster()));
                //mediaPoster.addActionListener(l -> showMediaInfo(m));   Temporarily commented until I can get showMediaInfo working
                favoriteSeriesPanel.add(mediaPoster);
           // }
        }

        favoriteSeriesContainer.add(favoriteSeriesPanel,BorderLayout.CENTER);
        mediaContainer.add(favoriteSeriesContainer);
    }
     //Temporarily snatched from SearchResult.java
        // private void showMediaInfo(Media m) {
        //     MediaInfoWindow info = new MediaInfoWindow(m);
        //     add(info, new Integer(1));
        //     info.setLocation(WIDTH/2-info.getWidth()/2, HEIGHT/2-info.getWidth()/2);
        //     info.setVisible(true);
        //     info.show();
        // }
}