package src.GUI;

import src.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

public class GUI extends JFrame implements Clickable {
    private static final long serialVersionUID = 1L;
    private User currentUser;
    private MediaDB db;

    private Container contentPane;
    private Container nContainer;
    private JLayeredPane centerContainer;

    private ArrayList<JButton> allButtons;

    private final int WIDTH = 1920, HEIGHT = 1080;

    public static void main(String[] args) {
        new GUI();
    }

    private void setupFrodo() {
        currentUser = new User("Frodo"); // for testpurposes
        currentUser.favorite(db.getMovies().get(0));
        currentUser.favorite(db.getMovies().get(2));
        currentUser.favorite(db.getMovies().get(3));
        currentUser.favorite(db.getSeries().get(0));
        currentUser.favorite(db.getSeries().get(2));
        currentUser.favorite(db.getSeries().get(3));
    }

    public GUI() {
        allButtons = new ArrayList<>();
        db = MediaDB.getInstance();
        setupFrodo();

        makeFrame();
        makeTopMenu();
        makeCenterContainer();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Initializes the JFrame, and get's the content pane and sets it.
    // Adds a ComponentListener
    private void makeFrame() {
        setSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        contentPane = getContentPane();


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
        nContainer = new Container();
        nContainer.setLayout(new BorderLayout());

        // Buttons used for the top menu
        // Favorites button added to the west of the main top menu container 'nContainer' in the western spot.
        Container wMenuContainer = new Container();
        wMenuContainer.setLayout(new GridLayout(1,2));

        JButton HomeButton = new JButton("Home");
        HomeButton.setPreferredSize(new Dimension(100, 50));
        HomeButton.addActionListener(e -> changeScenario(new MainMenu(currentUser, this)));
        allButtons.add(HomeButton);
        wMenuContainer.add(HomeButton);

        JButton favoritesButton = new JButton("Favorites");
        favoritesButton.setPreferredSize(new Dimension(100, 50));
        favoritesButton.addActionListener(e -> changeScenario(new Favorites(currentUser, this)));
        allButtons.add(favoritesButton);
        wMenuContainer.add(favoritesButton);

        nContainer.add(wMenuContainer, BorderLayout.WEST);

        // Search and User button added to the east menu container 'eMenuContainer'
        Container eMenuContainer = new Container();
        eMenuContainer.setLayout(new GridLayout(1,2));

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(l -> {
            SearchPopUp popup = new SearchPopUp(this, currentUser);
            centerContainer.add(popup, new Integer(1));
            popup.setVisible(true);
            popup.show();
        });
        allButtons.add(searchButton);

        JButton userButton = new JButton("User");
        allButtons.add(userButton);

        eMenuContainer.add(searchButton);
        eMenuContainer.add(userButton);

        // East menu container added to the north menu container 'nContainer' in the eastern spot.
        nContainer.add(eMenuContainer, BorderLayout.EAST);

        // The north menu container added to the content pane in the northern spot.
        contentPane.add(nContainer, BorderLayout.NORTH);
    }

    private void makeCenterContainer() {
        centerContainer = new MainMenu(currentUser, this);
        contentPane.add(centerContainer,BorderLayout.CENTER);
    }

    public void changeScenario(JLayeredPane container) {
        contentPane.removeAll();
        contentPane.add(nContainer, BorderLayout.NORTH);
        contentPane.add(container, BorderLayout.CENTER);
        centerContainer = container;
        pack();
    }

    public void buttonsSetEnabled(boolean b){
        for (JButton x : allButtons) {
            x.setEnabled(b);
        }
        if (centerContainer instanceof Clickable) {
            Clickable clickable = (Clickable) centerContainer;
            clickable.buttonsSetEnabled(b);
        }
    }
}
