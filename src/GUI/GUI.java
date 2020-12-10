package GUI;

import GUI.PopUps.*;
import Misc.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GUI extends JFrame implements Clickable {
    private static final long serialVersionUID = 1L;
    private User currentUser;

    private Container contentPane;
    private JPanel nContainer;
    private JLayeredPane centerContainer;

    private ArrayList<JButton> allButtons;

    private final int WIDTH = 1040, HEIGHT = 900;

    public static void main(String[] args) {
        MediaDB.getInstance();
        new GUI();
    }

    public GUI() {
        allButtons = new ArrayList<>();
        
        makeFrame();
    }

    // Initializes the JFrame, and gets the content pane and sets it.
    // Adds a ComponentListener
    private void makeFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        contentPane = getContentPane();
        makeTopMenu();
        makeCenterContainer();

        LoginPopUp loginOnStart = new LoginPopUp(this);
        centerContainer.add(loginOnStart, new Integer(1));
        loginOnStart.setLocation(WIDTH/2-loginOnStart.getWidth()/2, HEIGHT/2-loginOnStart.getWidth()/2);
        loginOnStart.setVisible(true);
        loginOnStart.show();

        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Makes the top menu that is located in the northern part of the content pane's BorderLayout
    private void makeTopMenu() {
        // Containers used for the top menu.
        nContainer = new JPanel();
        nContainer.setLayout(new BorderLayout());
        ColorTheme.paintAccentPanel(nContainer);

        // Buttons used for the top menu
        // Favorites button added to the west of the main top menu JPanel 'nContainer' in the western spot.
        JPanel wMenuContainer = new JPanel();
        wMenuContainer.setLayout(new GridLayout(1,2));

        JButton homeButton = new JButton("Home");
        try {
            homeButton.setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("buttons/buttons_home.png"))));
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
        homeButton.setPreferredSize(new Dimension(100, 50));
        homeButton.addActionListener(e -> changeScenario(new MainMenu(currentUser, this)));
        TextSettings.paintButtonFont(homeButton);
     
        ColorTheme.paintClickableButton(homeButton);
        allButtons.add(homeButton);
        wMenuContainer.add(homeButton);

        JButton favoritesButton = new JButton("Favorites");
        try {
            favoritesButton.setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("buttons/buttons_favourites.png"))));
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
        favoritesButton.setPreferredSize(new Dimension(100, 50));
        favoritesButton.addActionListener(e -> changeScenario(new Favorites(currentUser, this)));

        TextSettings.paintButtonFont(favoritesButton);
        ColorTheme.paintClickableButton(favoritesButton);

        allButtons.add(favoritesButton);
        wMenuContainer.add(favoritesButton);


        nContainer.add(wMenuContainer, BorderLayout.WEST);

        // Search and User button added to the east menu JPanel 'eMenuContainer'
        JPanel eMenuContainer = new JPanel();
        eMenuContainer.setLayout(new GridLayout(1,2));
        ColorTheme.paintAccentPanel(eMenuContainer);

        JButton searchButton = new JButton("Search");
        try {
            searchButton.setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("buttons/buttons_search.png"))));
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
        ColorTheme.paintClickableButton(searchButton);
        TextSettings.paintButtonFont(searchButton);

        searchButton.addActionListener(l -> {
            SearchPopUp popup = new SearchPopUp(currentUser, this);
            centerContainer.add(popup, new Integer(1));
            popup.setLocation(WIDTH/2-popup.getWidth()/2, 100);
            popup.setVisible(true);
            popup.show();
        });
        allButtons.add(searchButton);

        JButton userButton = new JButton("Logout");
        try {
            userButton.setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("buttons/buttons_logout.png"))));
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
        ColorTheme.paintClickableButton(userButton);
        TextSettings.paintButtonFont(userButton);

        userButton.addActionListener(e -> {
            changeScenario(new MainMenu(currentUser, this));
            currentUser = null;
            LoginPopUp loginPopUp = new LoginPopUp(this);
            centerContainer.add(loginPopUp, new Integer(1));
            loginPopUp.setLocation(WIDTH/2-loginPopUp.getWidth()/2, HEIGHT/2-loginPopUp.getWidth()/2);
            loginPopUp.setVisible(true);
            loginPopUp.show();
        });
        allButtons.add(userButton);

        eMenuContainer.add(searchButton);
        eMenuContainer.add(userButton);

        // East menu JPanel added to the north menu JPanel 'nContainer' in the eastern spot.
        nContainer.add(eMenuContainer, BorderLayout.EAST);

        // The north menu JPanel added to the content pane in the northern spot.
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
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
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

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public Dimension getCenterDimension() { return new Dimension(WIDTH-15,HEIGHT-78); }
    public Rectangle getCenterBounds() { return new Rectangle(0,0,WIDTH-15,HEIGHT-78); }
}