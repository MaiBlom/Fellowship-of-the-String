package GUI;

import GUI.PopUps.*;
import GUI.Scenarios.*;
import Misc.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GUI extends JFrame implements Clickable {
    private static final long serialVersionUID = 1L;
    private User currentUser;
    private static GUI instance;

    private Container contentPane;
    private JPanel nContainer;
    private JPanel wMenuContainer;
    private JPanel eMenuContainer;

    private Scenario currentScenario;

    private ArrayList<JButton> allButtons;

    private static final int WIDTH = 1040, HEIGHT = 900;

    public static void main(String[] args) {
        MediaDB.getInstance();
        instance = new GUI();
        instance.makeFrame();
    }

    public static GUI getInstance() {
        return instance;
    }

    private GUI() {
        super("Shire Streaming");
        allButtons = new ArrayList<>();
    }

    // Initializes the JFrame, and gets the content pane and sets it.
    // Adds a ComponentListener
    private void makeFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(ColorTheme.mainColor);

        contentPane = getContentPane();
        contentPane.setBackground(ColorTheme.mainColor);
        makeTopMenu();
        makeCenterContainer();

        LoginPopUp loginOnStart = new LoginPopUp();
        currentScenario.add(loginOnStart, new Integer(1));
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
        contentPane.add(nContainer, BorderLayout.NORTH);

        // Buttons used for the top menu
        // Favorites button added to the west of the main top menu JPanel 'nContainer' in the western spot.
        wMenuContainer = new JPanel();
        wMenuContainer.setLayout(new GridLayout(1,2));
        nContainer.add(wMenuContainer, BorderLayout.WEST);

        makeHomeButton();
        makeFavoritesButton();

        // Search and User button added to the east menu JPanel 'eMenuContainer'
        eMenuContainer = new JPanel();
        eMenuContainer.setLayout(new GridLayout(1,2));
        ColorTheme.paintAccentPanel(eMenuContainer);
        nContainer.add(eMenuContainer, BorderLayout.EAST);

        makeSearchButton();
        makeLogoutButton();

    }

    private void makeHomeButton() {
        JButton homeButton = new JButton("Home");
        try {
            homeButton.setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("buttons/buttons_home.png"))));
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
        homeButton.setPreferredSize(new Dimension(100, 50));
        homeButton.addActionListener(e -> changeScenario(Scenario.getMainMenu()));
        AssetDesigner.paintButtonFont(homeButton);

        ColorTheme.paintClickableButton(homeButton);
        allButtons.add(homeButton);
        wMenuContainer.add(homeButton);
    }

    private void makeFavoritesButton() {
        JButton favoritesButton = new JButton("Favorites");
        try {
            favoritesButton.setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("buttons/buttons_favourites.png"))));
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
        favoritesButton.setPreferredSize(new Dimension(100, 50));
        favoritesButton.addActionListener(e -> changeScenario(new Favorites()));

        AssetDesigner.paintButtonFont(favoritesButton);
        ColorTheme.paintClickableButton(favoritesButton);

        allButtons.add(favoritesButton);
        wMenuContainer.add(favoritesButton);
    }

    private void makeSearchButton() {
        JButton searchButton = new JButton("Search");
        try {
            searchButton.setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("buttons/buttons_search.png"))));
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
        ColorTheme.paintClickableButton(searchButton);
        AssetDesigner.paintButtonFont(searchButton);

        searchButton.addActionListener(l -> {
            SearchPopUp popup = new SearchPopUp();
            currentScenario.add(popup, new Integer(1));
            popup.setLocation(WIDTH/2-popup.getWidth()/2, 100);
            popup.setVisible(true);
            popup.show();
        });
        allButtons.add(searchButton);
        eMenuContainer.add(searchButton);
    }

    private void makeLogoutButton() {
        JButton userButton = new JButton("Logout");
        try {
            userButton.setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("buttons/buttons_logout.png"))));
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
        ColorTheme.paintClickableButton(userButton);
        AssetDesigner.paintButtonFont(userButton);

        userButton.addActionListener(e -> {
            changeScenario(Scenario.getMainMenu());
            currentUser = null;
            LoginPopUp loginPopUp = new LoginPopUp();
            currentScenario.add(loginPopUp, new Integer(1));
            loginPopUp.setLocation(WIDTH/2-loginPopUp.getWidth()/2, HEIGHT/2-loginPopUp.getWidth()/2);
            loginPopUp.setVisible(true);
            loginPopUp.show();
        });
        allButtons.add(userButton);
        eMenuContainer.add(userButton);
    }

    private void makeCenterContainer() {
        currentScenario = Scenario.getMainMenu();
        contentPane.add(currentScenario,BorderLayout.CENTER);
    }

    public void changeScenario(Scenario scenario) {
        contentPane.removeAll();
        contentPane.add(nContainer, BorderLayout.NORTH);
        contentPane.add(scenario, BorderLayout.CENTER);
        currentScenario = scenario;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        pack();
    }

    public void buttonsSetEnabled(boolean b){
        for (JButton x : allButtons) {
            x.setEnabled(b);
        }
        currentScenario.buttonsSetEnabled(b);
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }
    public User getCurrentUser() {
        return currentUser;
    }

    public static Dimension getCenterDimension() { return new Dimension(WIDTH-15,HEIGHT-78); }
    public static Rectangle getCenterBounds() { return new Rectangle(0,0,WIDTH-15,HEIGHT-78); }
}