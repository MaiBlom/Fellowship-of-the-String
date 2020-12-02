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

        JButton favoritesbButton = new JButton("Favorites");
        JButton searchButton = new JButton("Search");
        JButton userButton = new JButton("User");

        nContainer = new Container();
        nContainer.setLayout(new GridLayout(1, 3));

        nContainer.add(favoritesbButton, BorderLayout.EAST);
        nContainer.add(searchButton, BorderLayout.WEST);
        nContainer.add(userButton, BorderLayout.WEST);

        contentPane.add(nContainer, BorderLayout.NORTH);

        cContainer = new Container();
        cContainer.setLayout(new BorderLayout(3, 1));

        // Mai gave up right here and will continue to do stuff later tonight

        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private void setup() {
        db = MediaDB.getInstance();
        MediaReader fr = MediaReader.getInstance(db);
        fr.readAll();
    }
}
