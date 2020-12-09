package src.GUI;

import java.util.ArrayList;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import src.*;
import src.Media.*;
import src.GUI.PopUps.*;

public class MainMenu extends JLayeredPane implements Clickable {
    private static final long serialVersionUID = 1L;

    // The media database
    private MediaDB db;

    // Jorigin instance
    private GUI origin;
    private User currentUser;

    private ArrayList<JButton> allButtons;

    // 
    private Container movieIcons;
    private Container seriesIcons;

    // Int's to keep track of what movies to be displaying in the media lists.
    private int movieIndex, seriesIndex;

    // Integer that determines how many media icons to load
    private int mediaToShow = 5;

    // private final int WIDTH = 1050, HEIGHT = 720;

    // The constructor setting both index's to 0, and initializing the database.
    public MainMenu(User currentUser, GUI origin) {
        this.currentUser = currentUser;
        this.origin = origin;
        this.allButtons = new ArrayList<>();
        movieIndex = 0;
        seriesIndex = 0;
        setup();
    }

    // Initializes the database.
    private void setup() {
        db = MediaDB.getInstance();
        this.setPreferredSize(new Dimension(origin.getwidth()-12,origin.getheight()-82));
        makeMediaVisualiser();
    }

    // This method creates the containers for showing our ArrayList of JLabels that
    // hold the movie / series posters as their ImageIcons
    // This is also part of the content pane container, but in the CENTER of the
    // BorderLayout
    public void makeMediaVisualiser() {
        // Middle container
        Container contentPane = new Container();
        contentPane.setLayout(new GridLayout(3, 1));

        // Adds the 3 different media containers: (recommended media, movies, and
        // series)
        contentPane.add(makeRecommendedContainer());
        contentPane.add(makeMovieContainer());
        contentPane.add(makeSeriesContainer());
        contentPane.setBounds(0,0,origin.getwidth()-12,origin.getheight()-82);

        // Add the center container to the JLayeredPane
        this.add(contentPane, new Integer(0));
    }

    // Creates and returns the container containing the recommended media by
    // 'redaktionen'
    private Container makeRecommendedContainer() {
        // Main container for the recommended section.
        Container recommendedContainer = new Container();
        recommendedContainer.setLayout(new BorderLayout());

        // Sets the top text of the recommended media section,
        // and adds it to the main container.
        JLabel recommendedLabel = new JLabel("Recommended:");
        recommendedContainer.add(recommendedLabel, BorderLayout.NORTH);

        // Container for the displayed media, putting it to the left
        Container iconContainer = new Container();
        iconContainer.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Initializes the recommended media,
        // and loops through the objects to add them to the container.
        ArrayList<JButton> icons = loadRecommended();
        for (int i = 0; i < 3; i++) {
            iconContainer.add(icons.get(i));
        }

        // Adds the icon container to the main recommeded container.
        recommendedContainer.add(iconContainer, BorderLayout.CENTER);

        // Returns the recommended container.
        return recommendedContainer;
    }

    // This method loads the 3 recommended movies chosen by 'redaktionen',
    // and returns them as a JButton ArrayList.
    private ArrayList<JButton> loadRecommended() {
        ArrayList<JButton> recommended = new ArrayList<>(3);
        try {
            Movie movie1 = new Movie("The Lord of the Rings: The Fellowship of the Ring", 2001, new String[] { "Action", "Adventure", "Drama" },
            8.8, ImageIO.read(getClass().getClassLoader().getResourceAsStream("./res/redaktionfilm/fellowship of the ring.jpg")));
            Movie movie2 = new Movie("The Lord of the Rings: The Two Towers", 2002, new String[] { "Action", "Adventure", "Drama" },
            8.7, ImageIO.read(getClass().getClassLoader().getResourceAsStream("./res/redaktionfilm/two towers.jpg")));
            Movie movie3 = new Movie("The Lord of the Rings: Return of the King", 2003, new String[] { "Action", "Adventure", "Drama" },
            8.9, ImageIO.read(getClass().getClassLoader().getResourceAsStream("./res/redaktionfilm/return of the king.jpg")));

            ImageIcon image1 = new ImageIcon();
            image1.setImage(movie1.getPoster());
            JButton icon1 = new JButton();
            icon1.setIcon(image1);
            recommended.add(icon1);
            allButtons.add(icon1);

            ImageIcon image2 = new ImageIcon();
            image2.setImage(movie2.getPoster());
            JButton icon2 = new JButton();
            icon2.setIcon(image2);
            recommended.add(icon2);
            allButtons.add(icon2);

            ImageIcon image3 = new ImageIcon();
            image3.setImage(movie3.getPoster());
            JButton icon3 = new JButton();
            icon3.setIcon(image3);
            recommended.add(icon3);
            allButtons.add(icon3);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return recommended;
    }

    // Creates and returns the movie section container.
    private Container makeMovieContainer() {
        // Main container for the movie section.
        Container movieContainer = new Container();
        movieContainer.setLayout(new BorderLayout());

        // Sets the top text of the movie section,
        // and adds it to the main container.
        JLabel movieLabel = new JLabel("Movies:");
        movieContainer.add(movieLabel, BorderLayout.NORTH);

        // Container for the movie selector.
        Container movieSelectionContainer = new Container();
        movieSelectionContainer.setLayout(new BorderLayout());

        // Adds buttons to the movie selector and creates an ActionListener to them.
        JButton leftButton = new JButton("Left");
        allButtons.add(leftButton);
        leftButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if(movieIndex > mediaToShow && movieIndex <= 100) {
                    movieIndex -= mediaToShow;
                    movieIcons.removeAll();
                    loadMovies();
                    origin.pack();
                } else if(movieIndex < mediaToShow) {
                    movieIndex = 99 - mediaToShow;
                    movieIcons.removeAll();
                    loadMovies();
                    origin.pack();
                }
            }
            
        });
        JButton rightButton = new JButton("Right");
        allButtons.add(rightButton);
        rightButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if(movieIndex >= 0 && movieIndex < 100 - (mediaToShow * 2)) {
                    movieIndex += mediaToShow;
                    movieIcons.removeAll();
                    loadMovies();
                    origin.pack();
                } else if(movieIndex < mediaToShow) {
                    movieIndex = 0;
                    movieIcons.removeAll();
                    loadMovies();
                    origin.pack();
                }
            }
            
        });
        movieSelectionContainer.add(leftButton, BorderLayout.WEST);
        movieSelectionContainer.add(rightButton, BorderLayout.EAST);

        // Adds the movie icons container to the movie selector container.
        movieIcons = new Container();
        movieIcons.setLayout(new FlowLayout());
        movieSelectionContainer.add(movieIcons, BorderLayout.CENTER);

        // Loads the movies to show.
        loadMovies();

        // Adds the movie selector container to the movie section container.
        movieContainer.add(movieSelectionContainer,BorderLayout.CENTER);

        // Returns the movie section container.
        return movieContainer;
    } 

    // This method loads the (field) mediaToShow currently viewing movies based on movieIndex,
    // and adds them to the movieIcons container.
    private void loadMovies() {
        ArrayList<JButton> movieDisplayIcons = new ArrayList<JButton>(mediaToShow);

        // Loops through the movie database and loads (field) mediaToShow of them as JButton.
        for(int i = movieIndex; i < movieIndex + mediaToShow; i++){
            ImageIcon image = new ImageIcon();
            image.setImage(db.getMovies().get(i).getPoster());
            JButton icon = new JButton();
            icon.setIcon(image);
            movieDisplayIcons.add(icon);
            allButtons.add(icon);
        }

        // Adds all the JButtons to the movie icon container
        for(int i = 0; i < mediaToShow; i++) {
            movieIcons.add(movieDisplayIcons.get(i));
        }
    }

    // Creates and returns the series section container.
    private Container makeSeriesContainer() {
        // Main container for the series section.
        Container seriesContainer = new Container();
        seriesContainer.setLayout(new BorderLayout());

        // Sets the top label for the series section,
        // and adds it to the container.
        JLabel seriesLabel = new JLabel("Series:");
        seriesContainer.add(seriesLabel,BorderLayout.NORTH);

        // Container for the series selector
        Container seriesSelectorContainer = new Container();
        seriesSelectorContainer.setLayout(new BorderLayout());

        // Adds buttons to the series selector.
        JButton leftButton = new JButton("Left");
        allButtons.add(leftButton);
        leftButton.setSize(new Dimension(50, 50));
        leftButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if(seriesIndex > mediaToShow && seriesIndex <= 100) {
                    seriesIndex -= mediaToShow;
                    seriesIcons.removeAll();
                    loadSeries();
                    origin.pack();
                } else if(seriesIndex < mediaToShow) {
                    seriesIndex = 99 - mediaToShow;
                    seriesIcons.removeAll();
                    loadSeries();
                    origin.pack();
                }
            }
            
        });
        JButton rightButton = new JButton("Right");
        allButtons.add(rightButton);
        rightButton.setSize(new Dimension(50, 50));
        rightButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if(seriesIndex >= 0 && seriesIndex < 100 - (mediaToShow * 2)) {
                    seriesIndex += mediaToShow;
                    seriesIcons.removeAll();
                    loadSeries();
                    origin.pack();
                } else if(seriesIndex < mediaToShow) {
                    seriesIndex = 0;
                    seriesIcons.removeAll();
                    loadSeries();
                    origin.pack();
                }
            }
            
        });
        seriesSelectorContainer.add(leftButton, BorderLayout.WEST);
        seriesSelectorContainer.add(rightButton, BorderLayout.EAST);

        // Adds the series icon container to the series selector container.
        seriesIcons = new Container();
        seriesIcons.setLayout(new FlowLayout());
        seriesSelectorContainer.add(seriesIcons, BorderLayout.CENTER);

        // Loads the series to show.
        loadSeries();
        
        // Adds the series selector container to the series section container.
        seriesContainer.add(seriesSelectorContainer,BorderLayout.CENTER);

        // Returns the series section container
        return seriesContainer;
    }

    // This method loads the (field) mediaToShow currently viewing series based on seriesIndex,
    // and returns them as a JButton ArrayList.
    private void loadSeries() {
        ArrayList<JButton> seriesDisplayIcons = new ArrayList<JButton>(mediaToShow);

        // Loops through the series database and loads (field) mediaToShow of them as JButtons.
        for(int i = seriesIndex; i < seriesIndex + mediaToShow; i++){
            ImageIcon image = new ImageIcon();
            image.setImage(db.getSeries().get(i).getPoster());
            JButton icon = new JButton();
            icon.setIcon(image);
            seriesDisplayIcons.add(icon);
            allButtons.add(icon);
        }

        // Adds all the series JButtons to the series icon container.
        for(int i = 0; i < mediaToShow; i++) {
            seriesIcons.add(seriesDisplayIcons.get(i));
        }
    }

    public void buttonsSetEnabled(boolean b){
        for (JButton x : allButtons) {
            x.setEnabled(b);
        }
    }
}
