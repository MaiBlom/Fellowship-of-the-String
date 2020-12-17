package GUI.Scenarios;

import GUI.*;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Favorites extends Scenario {
    private static final long serialVersionUID = 1L;
    private final ArrayList<Media> favorites;

    public Favorites() {
        this.favorites = origin.getController().getCurrentUser().getFavorites();
        setup();
    }

    private void setup() {
        contentPane.setLayout(new BorderLayout());

        makeTopMenu();
        makeMediaContainer(favorites);

    }
    private void makeTopMenu() {
        JPanel topMenu = new JPanel();
        AssetDesigner.paintMainPanel(topMenu);
        topMenu.setLayout(new GridLayout(1,3));
        contentPane.add(topMenu,BorderLayout.NORTH);

        JLabel favoritesLabel = new JLabel("Favorites");
        AssetDesigner.paintHeader(favoritesLabel);
        JLabel resultsLabel = new JLabel("Total Results: " + favorites.size());
        AssetDesigner.paintHeader(resultsLabel);

        topMenu.add(favoritesLabel);
        topMenu.add(resultsLabel);
        AssetDesigner.paintMainPanel(topMenu);

        makeSorting(favorites);
        topMenu.add(sortingContainer);
    }
}
