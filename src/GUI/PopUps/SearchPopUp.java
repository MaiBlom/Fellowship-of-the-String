package GUI.PopUps;

import GUI.*;
import GUI.Scenarios.*;

import javax.swing.*;
import java.awt.*;

public class SearchPopUp extends PopUp {
    private static final long serialVersionUID = 1L;

    private JPanel centerContainer;
    private JPanel genresContainer;
    // search criteria fields
    private JTextField searchBar;
    private boolean searchMovies;
    private boolean searchSeries;
    private boolean[] searchGenres;
    private String[] selectableGenres;

    public SearchPopUp() {
        this.setPreferredSize(new Dimension(400,400));

        setup();
        pack();
        setVisible(true);
    }

    // Setup of the contentpane that consists of three elements, the top bar with a search bar, 
    // the middle with check boxes, and the bottom with a cancel and a search button.
    private void setup() {
        contentPane.setLayout(new BorderLayout());

        setupSearchBar();
        setupCenterContainer();
        setupBottomBar();
    }

    // Setup of the topbar with a free-text searchbar. 
    private void setupSearchBar() {
        JPanel searchBarContainer = new JPanel();
        AssetDesigner.paintMainPanel(searchBarContainer);
        searchBarContainer.setLayout(new GridLayout(2,1));
        contentPane.add(searchBarContainer, BorderLayout.NORTH);

        JLabel searchTitle = new JLabel("Search");
        AssetDesigner.paintMediaInfoFont(searchTitle);
        searchBarContainer.add(searchTitle);

        searchBar = new JTextField();
        searchBarContainer.add(searchBar);
    }

    // Setup of the center JPanel with a BorderLayout. This JPanel contains checkboxes for media 
    // type at the top and checkboxes for genres in the center.
    private void setupCenterContainer() {
        centerContainer = new JPanel();
        AssetDesigner.paintMainPanel(centerContainer);
        centerContainer.setLayout(new BorderLayout());
        contentPane.add(centerContainer, BorderLayout.CENTER);

        setupMediatypeContainer();
        setupGenreContainer();
    }

    // Setup of the media type checkboxes. Toggling the checkboxes will change the value of the two booleans
    // searchMovies and searchSeries which are used as search criteria.
    private void setupMediatypeContainer() {
        JPanel mediatypeContainer = new JPanel();
        AssetDesigner.paintMainPanel(mediatypeContainer);
        mediatypeContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
        centerContainer.add(mediatypeContainer, BorderLayout.NORTH);
        
        JCheckBox movieSearch = new JCheckBox("Movies  ");
        AssetDesigner.paintCheckBox(movieSearch);
        movieSearch.addItemListener(l -> searchMovies = l.getStateChange()==1);
        mediatypeContainer.add(movieSearch);

        JCheckBox seriesSearch = new JCheckBox("Series  ");
        AssetDesigner.paintCheckBox(seriesSearch);
        seriesSearch.addItemListener(l -> searchSeries = l.getStateChange()==1 );
        mediatypeContainer.add(seriesSearch);
    }

    // The following two methods are the setup of the genre checkboxes. Toggling the checkboxes 
    // will change the value of the booleans in the array searchGenres which are used as search criteria.
    private void setupGenreContainer() {
        genresContainer = new JPanel();
        AssetDesigner.paintMainPanel(genresContainer);
        genresContainer.setLayout(new BorderLayout());
        centerContainer.add(genresContainer, BorderLayout.CENTER);

        JLabel genresTitle = new JLabel("Genres");
        AssetDesigner.paintMediaInfoFont(genresTitle);
        genresContainer.add(genresTitle, BorderLayout.NORTH);

        setupGenres();
    }

    private void setupGenres() {
        JPanel allGenres = new JPanel();
        AssetDesigner.paintMainPanel(allGenres);
        allGenres.setLayout(new GridLayout(1,3));
        genresContainer.add(allGenres, BorderLayout.CENTER);

        JPanel leftGenres = new JPanel();
        AssetDesigner.paintMainPanel(leftGenres);
        leftGenres.setLayout(new BoxLayout(leftGenres, BoxLayout.Y_AXIS));
        allGenres.add(leftGenres);

        JPanel centerGenres = new JPanel();
        AssetDesigner.paintMainPanel(centerGenres);
        centerGenres.setLayout(new BoxLayout(centerGenres, BoxLayout.Y_AXIS));
        allGenres.add(centerGenres);

        JPanel rightGenres = new JPanel();
        AssetDesigner.paintMainPanel(rightGenres);
        rightGenres.setLayout(new BoxLayout(rightGenres, BoxLayout.Y_AXIS));
        allGenres.add(rightGenres);

        searchGenres = new boolean[23];
        selectableGenres = origin.getController().getSelectableGenres();

        for (int i = 0; i<23; i++) {
            JCheckBox genreBox = new JCheckBox(selectableGenres[i]);
            AssetDesigner.paintCheckBox(genreBox);
            genreBox.addItemListener(l -> updateGenreSearchCriteria(genreBox.getText(), l.getStateChange()==1));
            if (i<8) leftGenres.add(genreBox);
            else if (i<16) centerGenres.add(genreBox);
            else rightGenres.add(genreBox);
        }
    }

    // When a genre checkbox is toggled, this method is called. We'll loop through the selectableGenres array
    // and find the index of the genre that is toggled and then change the value in the searchGenres array on
    // the same index.
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

    // Setup of the bottom bar. The JPanel has two buttons: One to cancel the search and one to search
    // with the given criteria. 
    private void setupBottomBar() {
        JPanel bottomBar = new JPanel();
        AssetDesigner.paintMainPanel(bottomBar);
        bottomBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        contentPane.add(bottomBar, BorderLayout.SOUTH);
        
        JButton cancel = new JButton("Cancel");
        AssetDesigner.paintClickableButton(cancel);
        cancel.addActionListener(e -> dispose());
        bottomBar.add(cancel);

        JButton search = new JButton("Search");
        AssetDesigner.paintClickableButton(search);
        search.addActionListener(e -> clickOK(new SearchResult(searchBar.getText(), searchMovies, searchSeries, searchGenres)));
        bottomBar.add(search);
    }
}