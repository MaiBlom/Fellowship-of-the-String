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

public class SearchResult extends JLayeredPane {
    private MediaDB db;
    private Container contentPane;
    private JLabel yourSearch;
    // search criteria
    private String textSearch;
    private boolean searchMovies;
    private boolean searchSeries;
    private boolean[] searchGenres;
    private String[] selectableGenres;
        // Crime, Drama, Biography, Sport, History, Romance, War, Mystery, Adventure
        // Family, Fantasy, Thriller, Horror, Film-Noir, Action, Sci-fi, Comedy
        // Musical, Western, Music, Talk-show, Documentary, Animation
    private ArrayList<Media> results;

    private int WIDTH = 1000;
    private int HEIGHT = 700;
    
    public SearchResult(String ts, boolean sm, boolean ss, boolean[] sg) {
        textSearch = ts;
        searchMovies = sm;
        searchSeries = ss;
        searchGenres = sg;
        selectableGenres = new String[] {"Crime", "Drama", "Biography", "Sport", "History", "Romance", "War", "Mystery", "Adventure", "Family", "Fantasy", "Thriller", "Horror", "Film-Noir", "Action", "Sci-fi", "Comedy", "Musical", "Western", "Music", "Talk-show", "Documentary", "Animation"};
        db = MediaDB.getInstance();
        setup();
    }

    private void setup() {
        contentPane = new Container();
        contentPane.setLayout(new BorderLayout());
        this.add(contentPane, new Integer(-1));
        this.add(new JLabel("hej"), new Integer(0));
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        contentPane.setBounds(0,0,WIDTH,HEIGHT);
        makeSearchLabel();
        findResults();
        showResults();
    }

    private void makeSearchLabel() {
        StringBuilder sb = new StringBuilder("<html>You have searched for " + 
        (((searchMovies && searchSeries) || (!searchMovies && !searchSeries))? "movies and series." : "") + 
        ((searchMovies && !searchSeries)? "movies." : "") + ((!searchMovies && searchSeries)? "series." : ""));

        boolean anyGenres = false;
        boolean firstGenre = true;
        for (int i = 0; i<selectableGenres.length; i++) {
            if (searchGenres[i] == true) {
                if (anyGenres == false) {
                    sb.append("<br>Genres: ");
                    anyGenres = true;
                }
                if (firstGenre) firstGenre = false;
                else sb.append(", ");
                sb.append(selectableGenres[i]);
            }
        }

        sb.append("<br>Results: ");
        yourSearch = new JLabel(sb.toString());
        contentPane.add(yourSearch, BorderLayout.NORTH);
    }

    private void findResults() {
        results = new ArrayList<>();
        ArrayList<Media> temp = new ArrayList<>();
        if ((searchMovies && searchSeries) || (!searchMovies && !searchSeries)) temp = db.getAllMedia();
        else if (searchMovies) temp = db.getMovies();
        else temp = db.getSeries();

        Iterator<Media> it = temp.iterator();
        while (it.hasNext()) {
            Media m = it.next();
            boolean include = true;
            if (m.getTitle().contains(textSearch)) {
                String genres = Arrays.toString(m.getGenres());
                for (int i = 0; i<selectableGenres.length; i++) {
                    if (searchGenres[i]) {
                        if (!genres.contains(selectableGenres[i])) include = false;
                    }
                }
            } else include = false;
            
            if (include) results.add(m);
        }
        yourSearch.setText(yourSearch.getText()+results.size());
    }

    private void showResults() {
        Container allResults = new Container();
        allResults.setLayout(new FlowLayout(FlowLayout.LEFT));
        JScrollPane allResultsScroll = new JScrollPane(allResults,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        contentPane.add(allResultsScroll);

        for (Media m : results) {
            JButton mediaPoster = new JButton(new ImageIcon(m.getPoster()));
            mediaPoster.addActionListener(l -> showMediaInfo(m));
            allResults.add(mediaPoster);
        }
        allResults.setPreferredSize(new Dimension(this.getWidth(), 10000));
    }

    private void showMediaInfo(Media m) {
        MediaInfoWindow info = new MediaInfoWindow(m);
        add(info, new Integer(1));
        info.setLocation(WIDTH/2-info.getWidth()/2, HEIGHT/2-info.getWidth()/2);
        info.setVisible(true);
        info.show();
    }
}
