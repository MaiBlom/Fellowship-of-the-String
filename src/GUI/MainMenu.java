package src.GUI;

import java.util.ArrayList;
import java.awt.Container;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import src.MediaDB;
import src.MediaReader;
import src.Media.Movie;

public class MainMenu extends JLayeredPane {
    private static final long serialVersionUID = -5793860781733191506L;

    private MediaDB db;

    private int movieIndex, seriesIndex;

    public MainMenu() {
        movieIndex = 0;
        seriesIndex = 0;
        setup();
    }

    // This method creates the containers for showing our ArrayList of JLabels that
    // hold the movie / series posters as their ImageIcons
    // This is also part of the content pane container, but in the CENTER of the
    // BorderLayout
    public void makeMediaVisualiser(Container contentPane) {
        // Middle container
        Container cContainer = new Container();
        cContainer.setLayout(new GridLayout(3, 1));

        // Adds the 3 different media containers: (recommended media, movies, and series)
        cContainer.add(makeRecommendedContainer());
        cContainer.add(makeMovieContainer());
        cContainer.add(makeSeriesContainer());

        // Add the center container to the contentPane
        contentPane.add(cContainer, BorderLayout.CENTER);
    }

    // Creates and returns the container containing the recommended media by 'redaktionen'
    private Container makeRecommendedContainer() {
        Container recommendedContainer = new Container();
        recommendedContainer.setLayout(new BorderLayout());

        JLabel recommendedLabel = new JLabel("Recommended:");
        recommendedContainer.add(recommendedLabel,BorderLayout.NORTH);
        Container iconContainer = new Container();
        iconContainer.setLayout(new BorderLayout());

        Container recommendedIcons = new Container();
        recommendedIcons.setLayout(new FlowLayout(FlowLayout.LEFT));
        iconContainer.add(recommendedIcons, BorderLayout.CENTER);

        ArrayList<JLabel> icons = loadRecommended();
        for(int i = 0; i < 3; i++) {
            recommendedIcons.add(icons.get(i));
        }
        
        recommendedContainer.add(iconContainer,BorderLayout.CENTER);

        return recommendedContainer;
    }

    private ArrayList<JLabel> loadRecommended() {
        ArrayList<JLabel> recommended = new ArrayList<JLabel>(3);
        try {
            Movie movie1 = new Movie("The Lord of the Rings: The Fellowship of the Ring", 2001, new String[] {"Action", "Adventure", "Drama"},
            8.8, ImageIO.read(getClass().getClassLoader().getResourceAsStream("./res/redaktionfilm/fellowship of the ring.jpg")));
            Movie movie2 = new Movie("The Lord of the Rings: The Two Towers", 2002, new String[] {"Action","Adventure","Drama"}, 
            8.7, ImageIO.read(getClass().getClassLoader().getResourceAsStream("./res/redaktionfilm/two towers.jpg")));
            Movie movie3 = new Movie("The Lord of the Rings: Return of the King", 2003, new String[] {"Action", "Adventure", "Drama"},
            8.9, ImageIO.read(getClass().getClassLoader().getResourceAsStream("./res/redaktionfilm/return of the king.jpg")));

            ImageIcon image1 = new ImageIcon();
            image1.setImage(movie1.getPoster());
            JLabel icon1 = new JLabel();
            icon1.setIcon(image1);
            recommended.add(icon1);

            ImageIcon image2 = new ImageIcon();
            image2.setImage(movie2.getPoster());
            JLabel icon2 = new JLabel();
            icon2.setIcon(image2);
            recommended.add(icon2);

            ImageIcon image3 = new ImageIcon();
            image3.setImage(movie3.getPoster());
            JLabel icon3 = new JLabel();
            icon3.setIcon(image3);
            recommended.add(icon3);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
        return recommended;
    }

    private Container makeMovieContainer() {
        // Middle container movie list
        Container movieContainer = new Container();
        movieContainer.setLayout(new BorderLayout());

        JLabel movieLabel = new JLabel("Movies:");
        movieContainer.add(movieLabel,BorderLayout.NORTH);
        Container iconContainer = new Container();
        iconContainer.setLayout(new BorderLayout());

        //Adds buttons
        JButton leftButton = new JButton("Left");
        JButton rightButton = new JButton("Right");
        iconContainer.add(leftButton, BorderLayout.WEST);
        iconContainer.add(rightButton, BorderLayout.EAST);

        Container movieIcons = new Container();
        movieIcons.setLayout(new FlowLayout());
        iconContainer.add(movieIcons, BorderLayout.CENTER);

        ArrayList<JLabel> movieDisplayIcons = loadMovies();
        for(int i = 0; i < 6; i++) {
            movieIcons.add(movieDisplayIcons.get(i));
        }
        
        movieContainer.add(iconContainer,BorderLayout.CENTER);

        return movieContainer;
    }

    //This methods attempts to add the amount of movies correspondent to the size of the frame.
    private ArrayList<JLabel> loadMovies() {
        ArrayList<JLabel> movies = new ArrayList<JLabel>();

        for(int i = movieIndex; i < 6; i++){
            ImageIcon image = new ImageIcon();
            image.setImage(db.getMovies().get(i).getPoster());
            JLabel icon = new JLabel();
            icon.setIcon(image);
            movies.add(icon);
        }

        return movies;
    }

    private Container makeSeriesContainer() {
        // Middle container movie list
        Container seriesContainer = new Container();
        seriesContainer.setLayout(new BorderLayout());

        JLabel seriesLabel = new JLabel("Series:");
        seriesContainer.add(seriesLabel,BorderLayout.NORTH);
        Container iconContainer = new Container();
        iconContainer.setLayout(new BorderLayout());

        //Adds buttons lmao
        JButton leftButton = new JButton("Left");
        JButton rightButton = new JButton("Right");
        iconContainer.add(leftButton, BorderLayout.WEST);
        iconContainer.add(rightButton, BorderLayout.EAST);

        Container seriesIcons = new Container();
        seriesIcons.setLayout(new FlowLayout());
        iconContainer.add(seriesIcons, BorderLayout.CENTER);

        ArrayList<JLabel> icons = loadSeries();
        for(int i = 0; i < 10; i++) {
            seriesIcons.add(icons.get(i));
        }
        
        seriesContainer.add(iconContainer,BorderLayout.CENTER);

        return seriesContainer;
    }

    //This method attempts to add the amount of series correspondent to the size of the window
    //Requires a rework similar to the one done in loadMovies
    private ArrayList<JLabel> loadSeries() {
        ArrayList<JLabel> series = new ArrayList<JLabel>(10);
        for(int i = seriesIndex; i < seriesIndex+10; i++){
            ImageIcon image = new ImageIcon();
            image.setImage(db.getSeries().get(i).getPoster());
            JLabel icon = new JLabel();
            icon.setIcon(image);
            series.add(icon);
        }
        return series;
    }

    private void setup() {
        db = MediaDB.getInstance();
        MediaReader fr = MediaReader.getInstance(db);
        fr.readAll();
    }
}
