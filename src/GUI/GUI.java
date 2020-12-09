package src.GUI;

import src.*;
import src.GUI.PopUps.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI extends JFrame implements Clickable {
    private static final long serialVersionUID = 1L;
    private User currentUser;
    private MediaDB db;

    private Container contentPane;
    private JPanel nContainer;
    private JLayeredPane centerContainer;

    private ArrayList<JButton> allButtons;

    private final int WIDTH = 1040, HEIGHT = 900;

    public static void main(String[] args) {
        new GUI();
    }

    public GUI() {
        allButtons = new ArrayList<>();
        db = MediaDB.getInstance();
        
        makeFrame();
    }

    // Initializes the JFrame, and get's the content pane and sets it.
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
        homeButton.setPreferredSize(new Dimension(100, 50));
        homeButton.addActionListener(e -> changeScenario(new MainMenu(currentUser, this)));
        TextSettings.paintButtonFont(homeButton);
     
        ColorTheme.paintClickableButton(homeButton);
        allButtons.add(homeButton);
        wMenuContainer.add(homeButton);

        JButton favoritesButton = new JButton("Favorites");
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
        ColorTheme.paintClickableButton(userButton);
        userButton.setForeground(Color.white);                    //Temporary Font properties
        userButton.setFont(new Font("Verdana", Font.BOLD, 15));   //Temporary Font properties

        userButton.addActionListener(e -> {
            currentUser = null;
            changeScenario(new MainMenu(currentUser, this));
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
    public int getwidth() { return WIDTH; }
    public int getheight() { return HEIGHT; }
}
