package src.GUI;

import src.*;
import src.Media.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.IOException;

public class SearchPopUp extends JFrame {
    private Container contentPane;
    private Container searchBarContainer;
    private Container centerContainer;
    private Container mediatypeContainer;
    private Container genresContainer;
    private Container bottomBar;
    private Container allGenres;

    //searchcriteria
    private JTextField searchBar;
    private boolean searchMovies;
    private boolean searchSeries;
    private boolean[] searchGenres;
    private String[] selectableGenres;;
        // Crime, Drama, Biography, Sport, History, Romance, War, Mystery, Adventure
        // Family, Fantasy, Thriller, Horror, Film-Noir, Action, Sci-fi, Comedy
        // Musical, Western, Music, Talk-show, Documentary, Animation

    public SearchPopUp() {
        // super("Search", false, true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setup();
        pack();
        setVisible(true);
    }

    private void setup() {
        contentPane = super.getContentPane();
        contentPane.setLayout(new BorderLayout());

        setupSearchBar();
        setupCenterContainer();
        setupBottomBar();
    }

    private void setupSearchBar() {
        searchBarContainer = new Container();
        searchBarContainer.setLayout(new GridLayout(2,1));
        contentPane.add(searchBarContainer, BorderLayout.NORTH);

        JLabel searchTitle = new JLabel("Search");
        searchBarContainer.add(searchTitle);

        searchBar = new JTextField();
        searchBarContainer.add(searchBar);
    }

    private void setupCenterContainer() {
        centerContainer = new Container();
        centerContainer.setLayout(new BorderLayout());
        contentPane.add(centerContainer, BorderLayout.CENTER);

        setupMediatypeContainer();
        setupGenreContainer();
    }

    private void setupMediatypeContainer() {
        mediatypeContainer = new Container();
        mediatypeContainer.setLayout(new GridLayout());
        centerContainer.add(mediatypeContainer, BorderLayout.NORTH);
        
        JCheckBox movieSearch = new JCheckBox("Movies");
        movieSearch.addItemListener(l -> {
            searchMovies = l.getStateChange()==1 ? true : false;
        });

        JCheckBox seriesSearch = new JCheckBox("Series");
        seriesSearch.addItemListener(l -> {
            searchSeries = l.getStateChange()==1 ? true : false;
        });

        mediatypeContainer.add(movieSearch);
        mediatypeContainer.add(seriesSearch);
    }

    private void setupGenreContainer() {
        genresContainer = new Container();
        genresContainer.setLayout(new BorderLayout());
        centerContainer.add(genresContainer, BorderLayout.CENTER);

        JLabel genresTitle = new JLabel("Genres");
        genresContainer.add(genresTitle, BorderLayout.NORTH);

        setupGenres();
    }

    private void setupGenres() {
        allGenres = new Container();
        allGenres.setLayout(new GridLayout(1,3));
        genresContainer.add(allGenres, BorderLayout.CENTER);

        Container leftGenres = new Container();
        leftGenres.setLayout(new BoxLayout(leftGenres, BoxLayout.Y_AXIS));
        allGenres.add(leftGenres);
        Container centerGenres = new Container();
        centerGenres.setLayout(new BoxLayout(centerGenres, BoxLayout.Y_AXIS));
        allGenres.add(centerGenres);
        Container rightGenres = new Container();
        rightGenres.setLayout(new BoxLayout(rightGenres, BoxLayout.Y_AXIS));
        allGenres.add(rightGenres);

        searchGenres = new boolean[23];
        selectableGenres = new String[] {"Crime", "Drama", "Biography", "Sport", "History", "Romance", "War", "Mystery", "Adventure", 
                                         "Family", "Fantasy", "Thriller", "Horror", "Film-Noir", "Action", "Sci-fi", "Comedy",
                                         "Musical", "Western", "Music", "Talk-show", "Documentary", "Animation"};
        for (int i = 0; i<23; i++) {
            JCheckBox genreBox = new JCheckBox(selectableGenres[i]);
            genreBox.addItemListener(l -> {
                updateGenreSearchCriteria(genreBox.getText(), (l.getStateChange()==1 ? true : false));
            });
            if (i<8) leftGenres.add(genreBox);
            else if (i<16) centerGenres.add(genreBox);
            else rightGenres.add(genreBox);
        }
    }

    private void updateGenreSearchCriteria(String genre, boolean state) {
        int index = 0;
        for (int i = 0; i<23; i++) {
            if (selectableGenres[i].equals(genre)) {
                index = i;
                break;
            }
        }
        searchGenres[index] = state;
    }

    private void setupBottomBar() {
        bottomBar = new Container();
        bottomBar.setLayout(new BorderLayout());
        contentPane.add(bottomBar, BorderLayout.SOUTH);

        Container buttonContainer = new Container();
        buttonContainer.setLayout(new GridLayout(1,2));
        bottomBar.add(buttonContainer, BorderLayout.EAST);
        
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> clickCancel());
        buttonContainer.add(cancel);

        JButton search = new JButton("Search");
        search.addActionListener(e -> clickSearch());
        buttonContainer.add(search);
    }

    private void clickCancel() {
        // should cancel the search and return the user to the previous page.
    }
    private void clickSearch() {
        String searchCriteria = searchBar.getText();
        
        // should refresh the page with and show all media that fits the given parameters.
    }
}
