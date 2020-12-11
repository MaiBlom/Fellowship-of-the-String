package GUI.Scenarios;

import Comparators.*;
import GUI.*;
import Media.*;
import GUI.PopUps.*;
import Misc.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Favorites extends Scenario {
    private static final long serialVersionUID = 1L;
    private JPanel topMenu;
    private ArrayList<Media> favorites;

    public Favorites(User currentUser, GUI origin) {
        super(origin, currentUser);
        this.favorites = currentUser.getFavorites();
        setup();
    }

    private void setup() {
        contentPane.setLayout(new BorderLayout());

        makeTopMenu();
        makeMediaContainer(favorites);

    }
    private void makeTopMenu() {
        topMenu = new JPanel();
        ColorTheme.paintMainPanel(topMenu);
        topMenu.setLayout(new GridLayout(1,3));
        contentPane.add(topMenu,BorderLayout.NORTH);

        JLabel favoritesLabel = new JLabel("Favorites");
        TextSettings.paintHeader(favoritesLabel);
        JLabel resultsLabel = new JLabel("Total Results: " + favorites.size());
        TextSettings.paintHeader(resultsLabel);

        topMenu.add(favoritesLabel);
        topMenu.add(resultsLabel);
        ColorTheme.paintMainPanel(topMenu);

        makeSorting(favorites);
        topMenu.add(sortingContainer);
    }
}
