package GUI.PopUps;

import Model.*;
import GUI.*;
import GUI.Scenarios.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.IOException;

public class MediaInfoWindow extends PopUp {
    private static final long serialVersionUID = 1L;
    private final Media media;

    private JPanel westContainer;
    private JPanel centerContainer;
    private JPanel episodesContainer;

    public MediaInfoWindow(Media media) {
        this.media = media;
        this.setPreferredSize(new Dimension(600,400));
        
        setup();
        pack();
        setVisible(true);
    }

    // Setup of the main window of the pop-up. The contentpane is split up in two columns. The
    // westContainer holds image and the centerContainer holds the information.
    private void setup() {
        contentPane.setLayout(new BorderLayout());
        setupWestContainer();
        setupCenterContainer();
    }

    // Setup of the westContainer which holds the image of the media, the play-button and the
    // favorite-button.
    private void setupWestContainer() {
        westContainer = new JPanel();
        AssetDesigner.paintAccentPanel(westContainer);
        westContainer.setLayout(new BorderLayout(2,2));
        contentPane.add(westContainer, BorderLayout.WEST);

        // Adding the media poster to a container with FlowLayout to get the picture to sit in
        // the middle of the westContainer.
        JPanel mediaPosterContainer = new JPanel();
        mediaPosterContainer.setLayout(new FlowLayout());
        AssetDesigner.paintAccentPanel(mediaPosterContainer);
        westContainer.add(mediaPosterContainer, BorderLayout.NORTH);

        JLabel mediaPosterLabel = new JLabel();
        mediaPosterLabel.setIcon(new ImageIcon(media.getPoster()));
        mediaPosterContainer.add(mediaPosterLabel);

        setupLeftButtons();
    }

    // Create the two buttons. A bit of extra code for the favorites-button, since it has to
    // change button-text depending on whether the media is already in the current user's favorites.
    private void setupLeftButtons() {
        JPanel leftButtonsOuter = new JPanel();
        AssetDesigner.paintAccentPanel(leftButtonsOuter);
        leftButtonsOuter.setLayout(new FlowLayout());
        westContainer.add(leftButtonsOuter, BorderLayout.CENTER);

        JPanel leftButtons = new JPanel();
        AssetDesigner.paintAccentPanel(leftButtons);
        leftButtons.setLayout(new GridLayout(2,1));
        leftButtonsOuter.add(leftButtons);
        
        JButton playButton = new JButton();
        AssetDesigner.paintClickableButton(playButton);
        if (media instanceof Movie) playButton.setText("Play movie");
        else playButton.setText("Play");
        playButton.addActionListener(l -> clickOK(new PlayMediaPage(media)));
        leftButtons.add(playButton);

        JButton favorite = new JButton();
        AssetDesigner.paintClickableButton(favorite);
        if (origin.getController().getCurrentUser().isFavorite(media)) favorite.setText("Unfavorite");
        else favorite.setText("Favorite");
        favorite.addActionListener(l -> {
            if (origin.getController().getCurrentUser().isFavorite(media)) {
                origin.getController().getCurrentUser().unfavorite(media);
                favorite.setText("Favorite");
            } 
            else {
                origin.getController().getCurrentUser().favorite(media);
                favorite.setText("Unfavorite");
            }
        });
        leftButtons.add(favorite);
    }

    // Setup of the center JPanel which holds the information about the media. The JPanel is split
    // into two parts. The top is the information about the media (in the mediaInfo Container), and
    // the bottom is only for series, which is the information about the seasons.
    private void setupCenterContainer() {
        centerContainer = new JPanel();
        centerContainer.setLayout(new BorderLayout());
        AssetDesigner.paintMainPanel(centerContainer);
        contentPane.add(centerContainer, BorderLayout.CENTER);

        setupMediaInfo();
        if (media instanceof Series) setupSeasons();
    }

    // Setup of the mediaInfo container. The JPanel is split in four rows, each with an
    // information element about the media. 
    private void setupMediaInfo() {
        JPanel mediaInfo = new JPanel();
        AssetDesigner.paintMainPanel(mediaInfo);
        mediaInfo.setLayout(new GridLayout(4,1));

        // First row in the grid is the title of the media.
        JLabel title = new JLabel(media.getTitle());
        AssetDesigner.paintMediaInfoFont(title);
        mediaInfo.add(title);

        // Second row in the grid is the rating. The rating is found as a double and used
        // to crop the star-image at an appropriate length with the getSubimage() method.
        JPanel ratingContainerOuter = new JPanel();
        AssetDesigner.paintMainPanel(ratingContainerOuter);
        ratingContainerOuter.setLayout(new BorderLayout());
        JPanel ratingContainer = new JPanel();
        AssetDesigner.paintMainPanel(ratingContainer);
        ratingContainer.setLayout(new FlowLayout());

        double ratingVal = media.getRating();
        JLabel rating = new JLabel(" " + Double.toString(ratingVal));
        AssetDesigner.paintMediaInfoFont(rating);
        JLayeredPane bothStarImages = new JLayeredPane();
        
        try {

            BufferedImage emptyStarImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("emptyStarRating.png"));
            BufferedImage starImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("starRating.png"));

            JLabel emptyStars = new JLabel();
            emptyStars.setIcon(new ImageIcon(emptyStarImage));
            
            JLabel filledStars = new JLabel();
            filledStars.setIcon(new ImageIcon(starImage.getSubimage(0, 0, (int) (starImage.getWidth()*(ratingVal/10)), starImage.getHeight())));

            bothStarImages.setPreferredSize(new Dimension(emptyStarImage.getWidth(), emptyStarImage.getHeight()));

            bothStarImages.add(emptyStars, 0);
            bothStarImages.add(filledStars, 1);

            emptyStars.setBounds(0,0,emptyStarImage.getWidth(), emptyStarImage.getHeight()); 
            filledStars.setBounds(0,0,emptyStarImage.getWidth(), emptyStarImage.getHeight());

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());
            bothStarImages.setLayer(new JLabel("<Noget gik galt med indlæsningen af stjerner.>  "), 0);

        } catch (IOException e) {

            System.out.println(e.getMessage());
            bothStarImages.setLayer(new JLabel("<Noget gik galt med indlæsningen af stjerner.>  "), 0);

        }

        ratingContainer.add(bothStarImages);
        ratingContainer.add(rating);

        ratingContainerOuter.add(ratingContainer,BorderLayout.WEST);
        mediaInfo.add(ratingContainerOuter);

        // Third row is the release date / run time. The string is found differently depending
        // on the media type.
        JLabel release = new JLabel();
        AssetDesigner.paintMediaInfoFont(release);
        if (media instanceof Movie) release.setText(Integer.toString(media.getRelease()));
        else {
            Series s = (Series) media;
            release.setText(s.getRuntime());
        }
        mediaInfo.add(release);

        // Fourth row contains the genres. 
        StringBuilder genreBuilder = new StringBuilder("<html>Genres: ");
        String[] mediaGenres = media.getGenres();
        for (int i = 0; i<mediaGenres.length; i++) {
            genreBuilder.append(mediaGenres[i]);
            if (i != mediaGenres.length-1) genreBuilder.append(", ");
        }
        genreBuilder.append("</html>");
        JLabel genres = new JLabel(genreBuilder.toString());
        AssetDesigner.paintMediaInfoFont(genres);
        mediaInfo.add(genres);

        centerContainer.add(mediaInfo, BorderLayout.NORTH);
    }

    // Setup of the seasonsContainer at the bottom of the center container. The 
    // seasons JPanel have two parts. The top part contains a JComboBox element
    // where the user can choose what season to view.
    private void setupSeasons() {
        if (media instanceof Movie) return;

        JPanel seasonsContainer = new JPanel();
        AssetDesigner.paintMainPanel(seasonsContainer);
        seasonsContainer.setLayout(new BorderLayout());
        centerContainer.add(seasonsContainer, BorderLayout.CENTER);

        // The seasonsTopBar has the JLabel "Seasons", and the JComboBox element.

        JPanel seasonsTopBar = new JPanel();
        AssetDesigner.paintMainPanel(seasonsTopBar);
        seasonsTopBar.setLayout(new GridLayout(1,2));

        JLabel seasonsTitle = new JLabel("Seasons");
        AssetDesigner.paintMediaInfoFont(seasonsTitle);
        seasonsTopBar.add(seasonsTitle);

        // I convert the Media object to a Series object. Then I get the 
        // int[] with the number of episodes in each season. The length og this array
        // is the number of seasons in the series, so this number is used to create the
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
        AssetDesigner.paintComboBox(seasonList);
        seasonList.addActionListener(e -> showSeasonX((String) seasonList.getSelectedItem(), seasonEpisodes));
        seasonsTopBar.add(seasonList);
        seasonsContainer.add(seasonsTopBar, BorderLayout.NORTH);

        // The episodes JPanel is initialised empty, but buttons will be added to it in
        // the showSeasonX method.
        JPanel episodesFlowContainer = new JPanel();
        AssetDesigner.paintMainPanel(episodesFlowContainer);
        episodesFlowContainer.setLayout(new FlowLayout(FlowLayout.LEFT));

        episodesContainer = new JPanel();
        AssetDesigner.paintMainPanel(episodesContainer);
        episodesFlowContainer.add(episodesContainer);

        JScrollPane episodesScroll = new JScrollPane(episodesFlowContainer);
        AssetDesigner.paintScrollBar(episodesScroll);
        seasonsContainer.add(episodesScroll, BorderLayout.CENTER);
    }

    // We get the seasons-number and the number of episodes in each season. 
    private void showSeasonX(String s, int[] seasonEpisodes) {
        episodesContainer.removeAll();
        int numberOfEpisodes = 0;

        if (!s.equals("")) { 
            int seasonNumber = Integer.parseInt(s.split(" ")[1]);
            numberOfEpisodes = seasonEpisodes[seasonNumber-1];
            for (int i = 0; i<numberOfEpisodes; i++) {
                JButton button = new JButton("Episode " + (i+1));
                AssetDesigner.paintEpisodeButtons(button);
                button.addActionListener(e -> clickOK(new PlayMediaPage(media)));
                episodesContainer.add(button);
            } 
        } 

        // The episodes container's GridLayout is setup with 4 columns and as many rows as it need to show
        // all the episodes. There is a vertical and horisontal gap between the buttons with size 2.
        episodesContainer.setLayout(new GridLayout(numberOfEpisodes/4 + (numberOfEpisodes % 4 == 0? 0:1), 4, 2, 2));

        setPreferredSize(new Dimension(600,400));
        pack();
    }

    // Make a MediaInfoWindow popup with the given media.
    public static void showMediaInfo(Media m, JLayeredPane pane) {
        MediaInfoWindow info = new MediaInfoWindow(m);
        pane.add(info, new Integer(1));
        info.setLocation(214, 209);
        info.setVisible(true);
        info.show();
    }
}
