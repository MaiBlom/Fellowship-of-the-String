package GUI;

import Controller.MediaController;
import GUI.PopUps.*;
import GUI.Scenarios.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GUI extends JFrame implements Clickable {
    private static final long serialVersionUID = 1L;
    private static GUI instance;
    private MediaController controller;

    private Container contentPane;
    private JPanel nContainer;
    private JPanel wMenuContainer;
    private JPanel eMenuContainer;

    private Scenario currentScenario;

    private final ArrayList<JButton> allButtons;

    private static final int WIDTH = 1040, HEIGHT = 900;
    private static double scaling;

    public static GUI getInstance() {
        if (instance == null) {
            instance = new GUI();
            instance.controller = MediaController.getInstance();
            instance.makeFrame();
        }
        return instance;
    }

    private GUI() {
        super("Shire Streaming");
        allButtons = new ArrayList<>();

        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        if (screenDimension.getHeight() < HEIGHT) scaling = screenDimension.getHeight() / HEIGHT - 0.03;
        else scaling = 1;
    }

    // Initializes the JFrame, and gets the content pane and sets it.
    // Adds a ComponentListener
    private void makeFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension((int) (WIDTH*scaling), (int) (HEIGHT*scaling)));
        this.setBackground(AssetDesigner.mainColor);

        contentPane = getContentPane();
        contentPane.setBackground(AssetDesigner.mainColor);
        makeTopMenu();
        makeCenterContainer();

        LoginPopUp loginOnStart = new LoginPopUp();
        currentScenario.add(loginOnStart, new Integer(1));
        loginOnStart.setLocation(((int) (WIDTH*scaling))/2-loginOnStart.getWidth()/2, ((int) (HEIGHT*scaling))/2-loginOnStart.getWidth()/2);
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
        AssetDesigner.paintAccentPanel(nContainer);
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
        AssetDesigner.paintAccentPanel(eMenuContainer);
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
        homeButton.addActionListener(e -> changeScenario(Scenario.getMainMenu()));
        AssetDesigner.paintClickableButton(homeButton);

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
        favoritesButton.addActionListener(e -> changeScenario(new Favorites()));

        AssetDesigner.paintClickableButton(favoritesButton);

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
        AssetDesigner.paintClickableButton(searchButton);

        searchButton.addActionListener(l -> {
            SearchPopUp popup = new SearchPopUp();
            currentScenario.add(popup, new Integer(1));
            popup.setLocation(((int) (WIDTH*scaling))/2-popup.getWidth()/2, (int) (100*scaling));
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
        AssetDesigner.paintClickableButton(userButton);

        userButton.addActionListener(e -> {
            changeScenario(Scenario.getMainMenu());
            controller.setCurrentUser(null);
            LoginPopUp loginPopUp = new LoginPopUp();
            currentScenario.add(loginPopUp, new Integer(1));
            loginPopUp.setLocation(((int) (WIDTH*scaling))/2-loginPopUp.getWidth()/2, ((int) (HEIGHT*scaling))/2-loginPopUp.getWidth()/2);
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
        setPreferredSize(new Dimension((int) (WIDTH*scaling), (int) (HEIGHT*scaling)));
        pack();
    }

    public void buttonsSetEnabled(boolean b){
        for (JButton x : allButtons) {
            x.setEnabled(b);
        }
        currentScenario.buttonsSetEnabled(b);
    }

    public static double getScaling() { return scaling; }
    public MediaController getController() { return controller; }
    public Dimension getCenterDimension() { return new Dimension((int) ((WIDTH-15)*scaling),(int) ((HEIGHT-78)*scaling)); }
    public Rectangle getCenterBounds() { return new Rectangle(0,0,(int) ((WIDTH-15)*scaling),(int) ((HEIGHT-78)*scaling)); }
}