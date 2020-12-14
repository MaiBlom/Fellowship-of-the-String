package GUI.Scenarios;

import Controller.*;
import GUI.*;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class SearchResult extends Scenario {
    private static final long serialVersionUID = 1L;

    private JPanel topMenu;
    // search criteria
    private final String textSearch;
    private final boolean searchMovies;
    private final boolean searchSeries;
    private final boolean[] searchGenres;
    private ArrayList<Media> searchResults;
    
    // The search results object will be called from the SearchPopUp class with the given parameters.
    public SearchResult(String ts, boolean sm, boolean ss, boolean[] sg) {
        this.textSearch = ts;
        this.searchMovies = sm;
        this.searchSeries = ss;
        this.searchGenres = sg;
        this.searchResults = controller.search(textSearch,searchMovies,searchSeries,searchGenres);

        setup();
    }

    // Setup of the main page, which contains of a small description of what the search criteria are
    // and the results.
    private void setup() {
        contentPane.setLayout(new BorderLayout());

        makeTopMenu();
        makeMediaContainer(searchResults);
    }

    private void makeTopMenu() {
        topMenu = new JPanel();
        AssetDesigner.paintMainPanel(topMenu);
        topMenu.setLayout(new GridLayout());
        contentPane.add(topMenu, BorderLayout.NORTH);

        makeSearchLabel();
        makeSorting(searchResults);
        topMenu.add(sortingContainer);
    }

    // The search criteria are shown at the top of the window with this JLabel
    private void makeSearchLabel() {
        JLabel yourSearch = new JLabel(controller.getCurSearchLabel());
        AssetDesigner.paintHeader(yourSearch);
        topMenu.add(yourSearch);
    }
}