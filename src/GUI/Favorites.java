package GUI;

import Comparators.*;
import Media.*;
import GUI.PopUps.*;
import Misc.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Favorites extends JLayeredPane implements Clickable {
    private static final long serialVersionUID = 1L;
    private GUI origin;

    private JPanel contentPane;
    private JPanel topMenu;
    private JPanel mediaContainer;
    private User currentUser;
    private JLabel favoritesLabel;
    private JLabel resultsLabel;
    private int numberOfResults;
    private ArrayList<JButton> allResultButtons;
    private ArrayList<Media> favorites;

    public Favorites(User currentUser, GUI origin) {
        this.currentUser = currentUser;
        this.origin = origin;
        allResultButtons = new ArrayList<>(); //A list to keep track of all the JButtons so that we can operate with them later on.
        favorites = new ArrayList<>();
        setup();
    }

    private void setup() {
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        this.add(contentPane, new Integer(0));
        setPreferredSize(origin.getCenterDimension());
        contentPane.setBounds(origin.getCenterBounds());

        countResults();
        makeLabelContainer();
        makeMediaContainer();
        makeSorting();
    }
    private void makeLabelContainer() {
        topMenu = new JPanel();
        topMenu.setLayout(new GridLayout(1,3));

        favoritesLabel = new JLabel("Favorites");
        TextSettings.paintHeader(favoritesLabel);
        resultsLabel = new JLabel("Total Results: " + numberOfResults);
        TextSettings.paintHeader(resultsLabel);

        topMenu.add(favoritesLabel);
        topMenu.add(resultsLabel);
        ColorTheme.paintMainPanel(topMenu);

        contentPane.add(topMenu,BorderLayout.NORTH);

    }
    private void countResults() {
        numberOfResults = currentUser.getFavorites().size();
    }

    private void makeMediaContainer(){
        mediaContainer = new JPanel();
        ColorTheme.paintMainPanel(mediaContainer);
        mediaContainer.setLayout(new FlowLayout(FlowLayout.LEFT,15,0));
        contentPane.add(mediaContainer,BorderLayout.CENTER);

        createButtons();

        mediaContainer.setPreferredSize(new Dimension(6*150,(numberOfResults/6 + (numberOfResults % 6 == 0? 0:1))*250));
        origin.pack();

        JScrollPane allResultsScrollPane = new JScrollPane(mediaContainer);
        ColorTheme.paintScrollBar(allResultsScrollPane);
        contentPane.add(allResultsScrollPane);
    }
    private void makeSorting() {
        JPanel sortingContainer = new JPanel();
        ColorTheme.paintMainPanel(sortingContainer);
        sortingContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));

        String[] sortingOptions = {"Sort by...", "Title (A-Z)", "Title (Z-A)", "Release (newest to oldest)",
                "Release (oldest to newest)", "Rating (highest to lowest)", "Rating (lowest to highest)"};
        JComboBox<String> sortby = new JComboBox<>(sortingOptions);
        sortby.setBackground(ColorTheme.mainColor);
        sortby.setForeground(ColorTheme.textColor);
        sortby.setFont(new Font("Verdana", Font.BOLD, 15));
        sortby.addActionListener(e -> sort((String) sortby.getSelectedItem()));
        sortingContainer.add(sortby);

        topMenu.add(sortingContainer);
    }
    private void sort(String s) {
        if (s.equals("Title (A-Z)")) {
            Collections.sort(favorites, new TitleCompAZ());
        } else if (s.equals("Title (Z-A)")) {
            Collections.sort(favorites, new TitleCompZA());
        } else if (s.equals("Release (newest to oldest)")) {
            Collections.sort(favorites, new ReleaseCompDecreasing());
        } else if (s.equals("Release (oldest to newest)")) {
            Collections.sort(favorites, new ReleaseCompIncreasing());
        } else if (s.equals("Rating (highest to lowest)")) {
            Collections.sort(favorites, new RatingCompDecreasing());
        } else if (s.equals("Rating (lowest to highest)")) {
            Collections.sort(favorites, new RatingCompIncreasing());
        }
        mediaContainer.removeAll();
        allResultButtons.clear();
        createButtons();
        setPreferredSize(origin.getCenterDimension());
        contentPane.setBounds(origin.getCenterBounds());
        origin.pack();
    }

    private void createButtons() {
        ArrayList<Media> favorites = currentUser.getFavorites();

        for (Media m : favorites) {
            JButton mediaPoster = new JButton(new ImageIcon(m.getPoster()));
            allResultButtons.add(mediaPoster);
            mediaPoster.setText(m.getTitle());
            TextSettings.paintMediaButton(mediaPoster);
            mediaPoster.setVerticalTextPosition(SwingConstants.BOTTOM);
            mediaPoster.setHorizontalTextPosition(SwingConstants.CENTER);
            mediaPoster.setPreferredSize(new Dimension(150,250));
            ColorTheme.paintMediaButton(mediaPoster);
            mediaPoster.addActionListener(l -> MediaInfoWindow.showMediaInfo(m, origin, currentUser, this));
            mediaContainer.add(mediaPoster);
        }
    }

    public void buttonsSetEnabled(boolean b) {
        for (JButton x : allResultButtons) {
            x.setEnabled(b);
        }
    }
}
