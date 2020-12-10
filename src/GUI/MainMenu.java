package GUI;

import Media.*;
import GUI.PopUps.*;
import Misc.*;

import java.util.ArrayList;
import java.awt.event.*;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MainMenu extends JLayeredPane implements Clickable {
    private static final long serialVersionUID = 1L;

    // The media database
    private MediaDB db;

    // Jorigin instance
    private GUI origin;
    private User currentUser;

    private ArrayList<JButton> allButtons;
     
    // 
    private JPanel movieIcons;
    private JPanel seriesIcons;

    // Int's to keep track of what movies to be displaying in the media lists.
    private int movieIndex, seriesIndex;

    // Integer that determines how many media icons to load
    private int mediaPerRow = 5;

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
        this.setPreferredSize(origin.getCenterDimension());
        makeMediaVisualiser();
    }

    // This method creates the containers for showing our ArrayList of JLabels that
    // hold the movie / series posters as their ImageIcons
    // This is also part of the content pane container, but in the CENTER of the
    // BorderLayout
    public void makeMediaVisualiser() {
        // Middle container
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(3, 1));

        // Adds the 3 different media containers: (recommended media, movies, and
        // series)
        contentPane.add(makeRecommendedContainer());
        contentPane.add(makeMovieContainer());
        contentPane.add(makeSeriesContainer());
        contentPane.setBounds(origin.getCenterBounds());

        // Add the center JPanel to the JLayeredPane
        this.add(contentPane, new Integer(0));
    }

    // Creates and returns the JPanel containing the recommended media by
    // 'redaktionen'
    private JPanel makeRecommendedContainer() {
        // Main JPanel for the recommended section.
        JPanel recommendedContainer = new JPanel();
        recommendedContainer.setLayout(new BorderLayout());
        ColorTheme.paintMainPanel(recommendedContainer);

        // Sets the top text of the recommended media section,
        // and adds it to the main container.
        JLabel recommendedLabel = new JLabel("Recommended:");
        TextSettings.paintHeader(recommendedLabel);
        recommendedContainer.add(recommendedLabel, BorderLayout.NORTH);

        // JPanel for the displayed media, putting it to the left
        JPanel iconContainer = new JPanel();
        iconContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        ColorTheme.paintMainPanel(iconContainer);

        // Initializes the recommended media,
        // and loops through the objects to add them to the container.
        ArrayList<JButton> icons = loadRecommended();
        for (int i = 0; i < 3; i++) {
            iconContainer.add(icons.get(i));
        }

        // Adds the icon JPanel to the main recommeded container.
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
            8.8, ImageIO.read(getClass().getClassLoader().getResourceAsStream("redaktionfilm/fellowship of the ring.jpg")));
            Movie movie2 = new Movie("The Lord of the Rings: The Two Towers", 2002, new String[] { "Action", "Adventure", "Drama" },
            8.7, ImageIO.read(getClass().getClassLoader().getResourceAsStream("redaktionfilm/two towers.jpg")));

            Movie movie3 = (Movie) db.getMovies().get(32);

            Media[] movies = new Media[] {movie1, movie2, movie3};

            for(Media m : movies) {
                ImageIcon image = new ImageIcon(m.getPoster());
                JButton icon = new JButton(image);

                icon.setText(m.getTitle());
                TextSettings.paintMediaButton(icon);
                ColorTheme.paintMediaButton(icon);

                icon.setVerticalTextPosition(SwingConstants.BOTTOM);
                icon.setHorizontalTextPosition(SwingConstants.CENTER);
                icon.setPreferredSize(new Dimension(150,250));

                icon.addActionListener(l -> MediaInfoWindow.showMediaInfo(m, origin, currentUser, this));

                recommended.add(icon);
                allButtons.add(icon);
            }


        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return recommended;
    }

    // Creates and returns the movie section container.
    private JPanel makeMovieContainer() {
        // Main JPanel for the movie section.
        JPanel movieContainer = new JPanel();
        movieContainer.setLayout(new BorderLayout());
        ColorTheme.paintMainPanel(movieContainer);

        // Sets the top text of the movie section,
        // and adds it to the main container.
        JLabel movieLabel = new JLabel("Movies:");
        TextSettings.paintHeader(movieLabel);
        movieContainer.add(movieLabel, BorderLayout.NORTH);

        // JPanel for the movie selector.
        JPanel movieSelectionContainer = new JPanel();
        movieSelectionContainer.setLayout(new BorderLayout());
        ColorTheme.paintMainPanel(movieSelectionContainer);

        // Adds buttons to the movie selector and creates an ActionListener to them.
        JButton leftButton = new JButton();
        ColorTheme.paintArrowButtons(leftButton);
        try {
            leftButton.setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("buttons/buttons_left.png"))));
        } catch (IOException e) {}
        allButtons.add(leftButton);
        leftButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if(movieIndex >= mediaPerRow && movieIndex <= db.getMovies().size() - mediaPerRow) {
                    movieIndex -= mediaPerRow;
                    movieIcons.removeAll();
                    loadMovies();
                    origin.pack();
                } else if(movieIndex < mediaPerRow) {
                    movieIndex = 95;
                    movieIcons.removeAll();
                    loadMovies();
                    origin.pack();
                }
            }
            
        });
        JButton rightButton = new JButton();
        ColorTheme.paintArrowButtons(rightButton);
        try {
            rightButton.setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("buttons/buttons_right.png"))));
        } catch (IOException e) {}
        allButtons.add(rightButton);
        rightButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if(movieIndex >= 0 && movieIndex < db.getMovies().size() - mediaPerRow) {
                    movieIndex += mediaPerRow;
                    movieIcons.removeAll();
                    loadMovies();
                    origin.pack();
                } else if(movieIndex >= 95) {
                    movieIndex = 0;
                    movieIcons.removeAll();
                    loadMovies();
                    origin.pack();
                }
            }
        });
        movieSelectionContainer.add(leftButton, BorderLayout.WEST);
        movieSelectionContainer.add(rightButton, BorderLayout.EAST);

        // Adds the movie icons JPanel to the movie selector container.
        movieIcons = new JPanel();
        ColorTheme.paintMainPanel(movieIcons);
        movieIcons.setLayout(new FlowLayout());
        movieSelectionContainer.add(movieIcons, BorderLayout.CENTER);

        // Loads the movies to show.
        loadMovies();

        // Adds the movie selector JPanel to the movie section container.
        movieContainer.add(movieSelectionContainer,BorderLayout.CENTER);

        // Returns the movie section container.
        return movieContainer;
    } 

    // This method loads the (field) mediaPerRow currently viewing movies based on movieIndex,
    // and adds them to the movieIcons container.
    private void loadMovies() {
        ArrayList<JButton> movieDisplayIcons = new ArrayList<JButton>(mediaPerRow);

        // Loops through the movie database and loads (field) mediaPerRow of them as JButton.
        for(int i = movieIndex; i < movieIndex + mediaPerRow; i++){
            ImageIcon image = new ImageIcon();
            Media m = db.getMovies().get(i);
            image.setImage(m.getPoster());
            JButton icon = new JButton();            

            icon.setText(db.getMovies().get(i).getTitle());
            TextSettings.paintMediaButton(icon);
            icon.setVerticalTextPosition(SwingConstants.BOTTOM);
            icon.setHorizontalTextPosition(SwingConstants.CENTER);
            ColorTheme.paintMediaButton(icon);
            icon.setPreferredSize(new Dimension(150,250));

            icon.addActionListener(l -> MediaInfoWindow.showMediaInfo(m, origin, currentUser, this));

            icon.setIcon(image);

            movieDisplayIcons.add(icon);
            allButtons.add(icon);
        }

        // Adds all the JButtons to the movie icon container
        for(int i = 0; i < mediaPerRow; i++) {
            movieIcons.add(movieDisplayIcons.get(i));
        }
    }

    // Creates and returns the series section container.
    private JPanel makeSeriesContainer() {
        // Main JPanel for the series section.
        JPanel seriesContainer = new JPanel();
        ColorTheme.paintMainPanel(seriesContainer);
        seriesContainer.setLayout(new BorderLayout());

        // Sets the top label for the series section,
        // and adds it to the container.
        JLabel seriesLabel = new JLabel("Series:");
        TextSettings.paintHeader(seriesLabel);
        seriesContainer.add(seriesLabel,BorderLayout.NORTH);

        // JPanel for the series selector
        JPanel seriesSelectorContainer = new JPanel();
        seriesSelectorContainer.setLayout(new BorderLayout());

        // Adds buttons to the series selector.
        JButton leftButton = new JButton();
        ColorTheme.paintArrowButtons(leftButton);
        try {
            leftButton.setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("buttons/buttons_left.png"))));
        } catch (IOException e) {}
        allButtons.add(leftButton);
        leftButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if(seriesIndex >= mediaPerRow && seriesIndex <= 99) {
                    seriesIndex -= mediaPerRow;
                    seriesIcons.removeAll();
                    loadSeries();
                    origin.pack();
                } else if(seriesIndex < mediaPerRow) {
                    seriesIndex = 95;
                    seriesIcons.removeAll();
                    loadSeries();
                    origin.pack();
                }
            }
            
        });
        JButton rightButton = new JButton();
        ColorTheme.paintArrowButtons(rightButton);
        try {
            rightButton.setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("buttons/buttons_right.png"))));
        } catch (IOException e) {}
        allButtons.add(rightButton);
        rightButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if(seriesIndex >= 0 && seriesIndex < db.getMovies().size() - mediaPerRow) {
                    seriesIndex += mediaPerRow;
                    seriesIcons.removeAll();
                    loadSeries();
                    origin.pack();
                } else if(seriesIndex >= db.getMovies().size() - mediaPerRow) {
                    seriesIndex = 0;
                    seriesIcons.removeAll();
                    loadSeries();
                    origin.pack();
                }
            }
        });
        seriesSelectorContainer.add(leftButton, BorderLayout.WEST);
        seriesSelectorContainer.add(rightButton, BorderLayout.EAST);

        // Adds the series icon JPanel to the series selector container.
        seriesIcons = new JPanel();
        ColorTheme.paintMainPanel(seriesIcons);
        seriesIcons.setLayout(new FlowLayout());
        seriesSelectorContainer.add(seriesIcons, BorderLayout.CENTER);

        // Loads the series to show.
        loadSeries();
        
        // Adds the series selector JPanel to the series section container.
        seriesContainer.add(seriesSelectorContainer,BorderLayout.CENTER);

        // Returns the series section container
        return seriesContainer;
    }

    // This method loads the (field) mediaPerRow currently viewing series based on seriesIndex,
    // and returns them as a JButton ArrayList.
    private void loadSeries() {
        ArrayList<JButton> seriesDisplayIcons = new ArrayList<JButton>(mediaPerRow);

        // Loops through the series database and loads (field) mediaPerRow of them as JButtons.
        for(int i = seriesIndex; i < seriesIndex + mediaPerRow; i++){
            ImageIcon image = new ImageIcon();
            Media m = db.getSeries().get(i);
            image.setImage(m.getPoster());
            JButton icon = new JButton();

            icon.setText(db.getSeries().get(i).getTitle());
            TextSettings.paintMediaButton(icon);
            icon.setVerticalTextPosition(SwingConstants.BOTTOM);
            icon.setHorizontalTextPosition(SwingConstants.CENTER);
            ColorTheme.paintMediaButton(icon);
            icon.setPreferredSize(new Dimension(150,250));

            icon.addActionListener(l -> MediaInfoWindow.showMediaInfo(m, origin, currentUser, this));

            icon.setIcon(image);
            seriesDisplayIcons.add(icon);
            allButtons.add(icon);
        }

        // Adds all the series JButtons to the series icon container.
        for(int i = 0; i < mediaPerRow; i++) {
            seriesIcons.add(seriesDisplayIcons.get(i));
        }
    }

    public void buttonsSetEnabled(boolean b){
        for (JButton x : allButtons) {
            x.setEnabled(b);
        }
    }
}