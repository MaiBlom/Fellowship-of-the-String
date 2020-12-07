package src.GUI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class GUI extends JFrame {
    private static final long serialVersionUID = 1L;

    private JFrame frame;
    private Container contentPane;
    private MainMenu mainMenu;

    private final int WIDTH = 1920, HEIGHT = 1080;

    public static void main(String[] args) {
        new GUI();
    }

    public GUI() {
        makeFrame();
        makeTopMenu();
        
        mainMenu = new MainMenu(frame);
        mainMenu.makeMediaVisualiser(contentPane);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Initializes the JFrame, and get's the content pane and sets it.
    // Adds a ComponentListener
    private void makeFrame() {
        frame = new JFrame();
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        /* frame.addComponentListener(new ComponentListener() {
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
        }); */
    }

    // Makes the top menu that is located in the northern part of the content pane's BorderLayout
    private void makeTopMenu() {
        // Containers used for the top menu.
        Container nContainer = new Container();
        nContainer.setLayout(new BorderLayout());
        Container eMenuContainer = new Container();
        eMenuContainer.setLayout(new GridLayout(1,2));

        // Buttons used for the top menu
        JButton favoritesButton = new JButton("Favorites");
        favoritesButton.setPreferredSize(new Dimension(100, 50));
        JButton searchButton = new JButton("Search");
        JButton userButton = new JButton("User");

        // Favorites button added to the west of the main top menu container 'nContainer' in the western spot.
        nContainer.add(favoritesButton, BorderLayout.WEST);

        // Search and User button added to the east menu container 'eMenuContainer'
        eMenuContainer.add(searchButton);
        eMenuContainer.add(userButton);

        // East menu container added to the north menu container 'nContainer' in the eastern spot.
        nContainer.add(eMenuContainer, BorderLayout.EAST);

        // The north menu container added to the content pane in the northern spot.
        contentPane.add(nContainer, BorderLayout.NORTH);
    }
}
