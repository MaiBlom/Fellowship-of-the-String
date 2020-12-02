package src.GUI;

import src.MediaDB;
import src.*;

import javax.swing.*;
import java.awt.*;

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

    private final int WIDTH = 600, HEIGHT = 600;
    
    public static void main(String[] args) {
        new GUI();
    }

    public GUI() {
        makeFrame();
    }

    private void makeFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setup();
        contentPane = frame.getContentPane();
        contentPane.setLayout(new GridLayout(2, 1));

        // Objects for top menu
        JButton favoritesbButton = new JButton("Favorites");
        JButton searchButton = new JButton("Search");
        JButton userButton = new JButton("User");

        nContainer = new Container();
        nContainer.setLayout(new GridLayout(1, 3));

        // Add objects for top menu
        nContainer.add(favoritesbButton, BorderLayout.EAST);
        nContainer.add(searchButton, BorderLayout.WEST);
        nContainer.add(userButton, BorderLayout.WEST);

        contentPane.add(nContainer, BorderLayout.NORTH);

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
        JLabel movieTestLabel = new JLabel("Movies here");
        
        movieContainer.add(movieLabel);
        movieContainer.add(movieTestLabel);

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

        //frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private void setup() {
        db = MediaDB.getInstance();
        MediaReader fr = MediaReader.getInstance(db);
        fr.readAll();
    }
}
