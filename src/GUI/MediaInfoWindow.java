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

public class MediaInfoWindow extends JInternalFrame {
    private Media media;
    private Container contentPane;
    private Container westContainer;
    private Container centerContainer;
    private Container mediaInfo;
    private Container seasonsContainer;
    private Container episodesContainer;

    public MediaInfoWindow(Media media) {
        super(media.getTitle(), false, true);
        this.media = media;
        setup();
        pack();
        setVisible(true);
    }

    // Setup of the main window of the pop-up.
    // The contentpane is split up in two columns. The westContainer holds image 
    // and the centerContainer holds the information.
    private void setup() {
        contentPane = super.getContentPane();
        contentPane.setLayout(new BorderLayout());

        setupWestContainer();
        setupCenterContainer();
    }

    // Setup of the westContainer which holds the image of the media 
    // (and soon also a "play" button).
    private void setupWestContainer() {
        westContainer = new Container();
        westContainer.setLayout(new FlowLayout());

        // Is there a better way of inserting images?
        ImageIcon image = new ImageIcon();
        image.setImage(media.getPoster());
        JLabel mediaPosterLabel = new JLabel();
        mediaPosterLabel.setIcon(image);
        contentPane.add(mediaPosterLabel);
        westContainer.add(mediaPosterLabel);

        contentPane.add(westContainer, BorderLayout.WEST);
    }

    // Setup of the center container which holds the information about the media.
    // The container is split into two parts. The top is the information about the media
    // (in the mediaInfo Container), and the bottom is only for series, which is the
    // information about the seasons.
    private void setupCenterContainer() {
        centerContainer = new Container();
        centerContainer.setLayout(new BorderLayout());
        contentPane.add(centerContainer, BorderLayout.CENTER);

        setupMediaInfo();
        if (media instanceof Series) setupSeasons();
    }

    // Setup of the mediaInfo container. The container is split in four rows, each with an
    // information element about the media. 
    private void setupMediaInfo() {
        mediaInfo = new Container();
        mediaInfo.setLayout(new GridLayout(4,1));

        // First row in the grid is the title of the media.
        JLabel title = new JLabel(media.getTitle());
        mediaInfo.add(title);

        // Second row in the grid is the rating. The rating is found as a double and used
        // to crop the star-image at an appropriate length with the getSubimage() method.
        Container ratingContainerOuter = new Container();
        ratingContainerOuter.setLayout(new BorderLayout());
        Container ratingContainer = new Container();
        ratingContainer.setLayout(new FlowLayout());

        double ratingVal = media.getRating();
        JLabel rating = new JLabel(" " + Double.toString(ratingVal));
        
        BufferedImage starImage;
        try {
            starImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("./res/starRating.png"));
        } catch (IOException e) {
            starImage = null;
        }
        JLabel stars = new JLabel();
        stars.setIcon(new ImageIcon(starImage.getSubimage(0, 0, (int) (starImage.getWidth()*(ratingVal/10)), starImage.getHeight())));

        ratingContainer.add(stars);
        ratingContainer.add(rating);

        ratingContainerOuter.add(ratingContainer,BorderLayout.WEST);
        mediaInfo.add(ratingContainerOuter);

        // Third row is the release date / run time. The string is found differently depending
        // on the media type.
        JLabel release = new JLabel();
        if (media instanceof Movie) release.setText(Integer.toString(media.getRelease()));
        else {
            Series s = (Series) media;
            release.setText(s.getRuntime());
        }
        mediaInfo.add(release);

        // Fourth row contains the genres. 
        StringBuilder genreBuilder = new StringBuilder("Genres: ");
        String[] mediaGenres = media.getGenres();
        for (int i = 0; i<mediaGenres.length; i++) {
            genreBuilder.append(mediaGenres[i]);
            if (i != mediaGenres.length-1) genreBuilder.append(", ");
        }
        JLabel genres = new JLabel(genreBuilder.toString());
        mediaInfo.add(genres);

        centerContainer.add(mediaInfo, BorderLayout.NORTH);
    }

    // Setup of the seasonsContainer at the bottom of the center container. The 
    // seasons container have two parts. The top part contains a JComboBox element
    // where the user can choose what season to view.
    private void setupSeasons() {
        if (media instanceof Movie) return;

        seasonsContainer = new Container();
        seasonsContainer.setLayout(new BorderLayout());
        centerContainer.add(seasonsContainer, BorderLayout.CENTER);

        // The seasonsTopBar has the JLabel "Seasons", and the JComboBox element.

        Container seasonsTopBar = new Container();
        seasonsTopBar.setLayout(new GridLayout(1,2));

        JLabel seasonsTitle = new JLabel("Seasons");
        seasonsTopBar.add(seasonsTitle);

        // I convert the Media object to a Series object. Then I get the 
        // int[] with the number of episodes in each season. The length og this array
        // is the nuumber of seasons in the series, so this number is used to create the 
        // String[] comboboxLabels, which is filled with "Season 1", "Season 2" ...
        // The combobox is created with the given labels, and an ActionListener is added.
        // The ActionListener needs the seasonEpisodes array to create buttons.
        Series s = (Series) media;
        int[] seasonEpisodes = s.getSeason();
        String[] comboboxLabels = new String[seasonEpisodes.length+1];
        for (int i = 0; i<comboboxLabels.length; i++) {
            if (i == 0) comboboxLabels[i] =  "";
            else comboboxLabels[i] = "Season " + (i);
        }
        JComboBox<String> seasonList = new JComboBox<>(comboboxLabels);
        seasonList.addActionListener(e -> showSeasonX((String) seasonList.getSelectedItem(), seasonEpisodes));
        seasonsTopBar.add(seasonList);

        seasonsContainer.add(seasonsTopBar, BorderLayout.NORTH);

        // The episodes container is initialised empty, but buttons will be added to it in
        // the showSeasonX method.
        episodesContainer = new Container();
        episodesContainer.setLayout(new FlowLayout());

        seasonsContainer.add(episodesContainer, BorderLayout.CENTER);
    }

    // We get the seasons-number and the number of episodes in each season. 
    private void showSeasonX(String s, int[] seasonEpisodes) {
        episodesContainer.removeAll();
        if (!s.equals("")) { 
            int seasonNumber = Character.getNumericValue(s.charAt(s.length()-1));
            int numberOfEpisodes = seasonEpisodes[seasonNumber-1];

            for (int i = 0; i<numberOfEpisodes; i++) {
                JButton button = new JButton("Episode " + (i+1));
                button.addActionListener(e -> clickOnEpisode());
                episodesContainer.add(button);
            }
        }
        pack();
    }

    private void clickOnEpisode() {
        // vinduet skal lukkes, main frame skal opdatere til video
    }
}