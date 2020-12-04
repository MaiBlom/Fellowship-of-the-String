package src.GUI;

import src.MediaDB;
import src.Media.Movie;
import src.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

// This GUI is WIP, we use it for testing for now.

public class GUI extends JFrame {
    private MediaDB db;
    private JFrame frame;
    private Container contentPane;
    private Container nContainer;
    private Container cContainer;
    private Container recommendedContainer;
    private Container movieContainer;
    private Container seriesContainer;
    private Container eMenuContainer;
    private int movieIndex, seriesIndex;
    private ArrayList<JLabel> movieDisplayIcons;

    private final int WIDTH = 1920, HEIGHT = 1080;

    public static void main(String[] args) {
        new GUI();
    }

    public GUI() {
        movieIndex = 0;
        seriesIndex = 0;
        makeFrame();
        makeTopMenu();
        makeMediaVisualiser();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void makeFrame() {
        frame = new JFrame();
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setup();
        contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        frame.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e){
                Component c = (Component)e.getSource();
                c.repaint();
                //The purpose of this component is to be able to repaint the frame when the window is resized although IT DOESNT WORK
            }
            @Override
            public void componentMoved(ComponentEvent e) {
            }
            @Override
            public void componentShown(ComponentEvent e) {
            }
            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });
    }
    //Makes the top menu that is located in the North part of the content pane BorderLayout
    private void makeTopMenu() {
        // Objects for top menu
        JButton favoritesButton = new JButton("Favorites");
        favoritesButton.setPreferredSize(new Dimension(100, 50));
        JButton searchButton = new JButton("Search");
        JButton userButton = new JButton("User");

        //Containers
        nContainer = new Container();
        nContainer.setLayout(new BorderLayout());

        // Add objects for top menu
        eMenuContainer = new Container();
        eMenuContainer.setLayout(new GridLayout(1,2));

        nContainer.add(favoritesButton, BorderLayout.WEST);
        eMenuContainer.add(searchButton);
        eMenuContainer.add(userButton);
        
        nContainer.add(eMenuContainer, BorderLayout.EAST);
        

        contentPane.add(nContainer, BorderLayout.NORTH);
    }
    //This method creates the containers for showing our ArrayList of JLabels that hold the movie / series posters as their ImageIcons
    //This is also part of the content pane container, but in the CENTER of the BorderLayout
    private void makeMediaVisualiser() {
        // Middle container
        cContainer = new Container();
        cContainer.setLayout(new GridLayout(3, 1));

        // Middle container recommended by "redaktionen"
        makeRecommendedContainer();

        // movies
        makeMovieContainer();

        // Middle container series list
        seriesContainer = new Container();
        seriesContainer.setLayout(new BorderLayout());

        JLabel seriesLabel = new JLabel("Series:");
        makeSeriesContainer();
        
        
        seriesContainer.add(seriesLabel,BorderLayout.NORTH);
        //seriesContainer.add(seriesTestLabel,BorderLayout.CENTER);

        // Add the 3 media containers to the center container
        cContainer.add(recommendedContainer);
        cContainer.add(movieContainer);
        cContainer.add(seriesContainer);

        // Add the center container to the contentPane
        contentPane.add(cContainer);

    }

    private void makeMovieContainer() {


        // Middle container movie list
        movieContainer = new Container();
        movieContainer.setLayout(new BorderLayout());

        JLabel movieLabel = new JLabel("Movies:");
        movieContainer.add(movieLabel,BorderLayout.NORTH);
        Container iconContainer = new Container();
        iconContainer.setLayout(new BorderLayout());

        //Adds buttons lmao
        JButton leftButton = new JButton("Left");
        JButton rightButton = new JButton("Right");
        iconContainer.add(leftButton, BorderLayout.WEST);
        iconContainer.add(rightButton, BorderLayout.EAST);

        Container movieIcons = new Container();
        movieIcons.setLayout(new FlowLayout());
        iconContainer.add(movieIcons, BorderLayout.CENTER);

        movieDisplayIcons = loadMovies();
        for(int i = 0; i < frame.getWidth()/200; i++) {
            movieIcons.add(movieDisplayIcons.get(i));
        }
        
        movieContainer.add(iconContainer,BorderLayout.CENTER);
    }

    //This methods attempts to add the amount of movies correspondent to the size of the frame.
    private ArrayList<JLabel> loadMovies() {
        ArrayList<JLabel> movies = new ArrayList<JLabel>();
        for(int i = movieIndex; i < frame.getWidth()/200; i++){
            ImageIcon image = new ImageIcon();
            image.setImage(db.getMovies().get(i).getPoster());
            JLabel icon = new JLabel();
            icon.setIcon(image);
            movies.add(icon);
        }
        return movies;
    }

    private void makeSeriesContainer() {
        // Middle container movie list
        seriesContainer = new Container();
        seriesContainer.setLayout(new BorderLayout());

        JLabel seriesLabel = new JLabel("Movies:");
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

    private void makeRecommendedContainer() {
        recommendedContainer = new Container();
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
    }

    private ArrayList<JLabel> loadRecommended() {
        ArrayList<JLabel> recommended = new ArrayList<JLabel>(3);
        try{
            Movie movie1 = new Movie("The Lord of the Rings: The Fellowship of the Ring", 2001, new String[] {"Action", "Adventure", "Drama"},
            8.8, ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/redaktionfilm/fellowship of the ring.jpg")));
            Movie movie2 = new Movie("The Lord of the Rings: The Two Towers", 2002, new String[] {"Action","Adventure","Drama"}, 
            8.7, ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/redaktionfilm/two towers.jpg")));
            Movie movie3 = new Movie("The Lord of the Rings: Return of the King", 2003, new String[] {"Action", "Adventure", "Drama"},
            8.9, ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/redaktionfilm/return of the king.jpg")));

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
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return recommended;
    }

    private void setup() {
        db = MediaDB.getInstance();
        MediaReader fr = MediaReader.getInstance(db);
        fr.readAll();
    }

    public GUI getInstance() {
        return this;
    }
}
