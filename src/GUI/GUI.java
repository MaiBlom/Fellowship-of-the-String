package src.GUI;

import src.MediaDB;
import src.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// This GUI is WIP, we use it for testing for now.

public class GUI extends JFrame {
    private MediaDB db;
    private JFrame frame;
    private Container contentPane;
    private Container nContainer;
    private Container cContainer;
    private Container redakAnbContainer;
    private Container movieContainer;
    private Container seriesContainer;
    private Container eMenuContainer;
    private int movieIndex,seriesIndex;

    private final int WIDTH = 600, HEIGHT = 600;
    
    public static void main(String[] args) {
        new GUI();
    }

    public GUI() {
        movieIndex = 0;
        seriesIndex = 0;
        makeFrame();
        makeTopMenu();
        makeMediaVisualiser();

        //frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void makeFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setup();
        contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
    }
    
    private void makeTopMenu() {
               // Objects for top menu
               JButton favoritesButton = new JButton("Favorites");
               favoritesButton.setPreferredSize(new Dimension(100, 50));
               JButton searchButton = new JButton("Search");
               JButton userButton = new JButton("User");
       
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
 
    private void makeMediaVisualiser() {
        // Middle container
        cContainer = new Container();
        cContainer.setLayout(new GridLayout(3, 1));

        // Middle container recommended by "redaktionen"
        redakAnbContainer = new Container();
        redakAnbContainer.setLayout(new GridLayout(2,1));
        
        JLabel redakLabel = new JLabel("Redaktionen anbefaler:");
        JLabel mediaTestLabel = new JLabel("Recommendations here");
        
        redakAnbContainer.add(redakLabel);
        redakAnbContainer.add(mediaTestLabel);

        // Middle container movie list
        movieContainer = new Container();
        movieContainer.setLayout(new GridLayout(2,1));

        JLabel movieLabel = new JLabel("Movies:");
        movieContainer.add(movieLabel);
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

        ArrayList<JLabel> icons = loadMovies();
        for(int i = 0; i < 10; i++) {
            movieIcons.add(icons.get(i));
        }
        
        movieContainer.add(iconContainer);

        // Middle container series list
        seriesContainer = new Container();
        seriesContainer.setLayout(new GridLayout(2,1));

        JLabel seriesLabel = new JLabel("Series:");
        JLabel seriesTestLabel = new JLabel("Series here");
        
        seriesContainer.add(seriesLabel);
        seriesContainer.add(seriesTestLabel);

        // Add the 3 media containers to the center container
        cContainer.add(redakAnbContainer);
        cContainer.add(movieContainer);
        cContainer.add(seriesContainer);

        // Add the center container to the contentPane
        contentPane.add(cContainer);
    }

    private ArrayList<JLabel> loadMovies() {
        ArrayList<JLabel> movies = new ArrayList<JLabel>(10);
        for(int i = movieIndex; i < movieIndex+10; i++){
            ImageIcon image = new ImageIcon();
            image.setImage(db.getMovies().get(i).getPoster());
            JLabel icon = new JLabel();
            icon.setIcon(image);
            movies.add(icon);
        }
        return movies;
    }


    private void setup() {
        db = MediaDB.getInstance();
        MediaReader fr = MediaReader.getInstance(db);
        fr.readAll();
    }
}
