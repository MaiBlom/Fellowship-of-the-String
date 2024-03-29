package GUI.Scenarios;

import GUI.*;
import Model.*;
import GUI.PopUps.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MainMenu extends Scenario {
    private static final long serialVersionUID = 1L;

    private JPanel recommendedIcons;
    private JPanel movieIcons;
    private JPanel seriesIcons;

    // Integers to keep track of what movies to be displaying in the media lists.
    private int movieIndex, seriesIndex;

    // Integer that determines how many media icons to load
    private final int mediaPerRow = 5;

    // The constructor setting both index's to 0, and initializing the database.
    public MainMenu() {
        this.movieIndex = 0;
        this.seriesIndex = 0;

        setup();
    }

    // This method creates the containers for showing our ArrayList of JLabels that
    // hold the movie / series posters as their ImageIcons
    // This is also part of the content pane container, but in the CENTER of the
    // BorderLayout
    private void setup() {
        // Middle container
        contentPane.setLayout(new GridLayout(3, 1));

        // Adds the 3 different media containers: (recommended media, movies, and series)
        contentPane.add(makeScrollPanel(1));
        contentPane.add(makeScrollPanel(2));
        contentPane.add(makeScrollPanel(3));
    }

    private JPanel makeScrollPanel(int row) {
        if (row < 1 || row > 3) return null;
        boolean isSeries = (row != 2);

        // Main panel for the container with a BorderLayout that has a title in the
        // NORTH section and the media in the CENTER section.
        JPanel mediaContainer = new JPanel();
        mediaContainer.setLayout(new BorderLayout());
        AssetDesigner.paintMainPanel(mediaContainer);

        // Set the title of the section and add it to the NORTH part of the main container.
        String mediaType;
        if (row == 1) mediaType = " Recommended: ";
        else if (row == 2) mediaType = " Movies: ";
        else mediaType = " Series: ";
        JLabel headerLabel = new JLabel(mediaType);
        AssetDesigner.paintHeader(headerLabel);
        mediaContainer.add(headerLabel, BorderLayout.NORTH);

        // The panel with the media in and left/right buttons.
        JPanel mediaSelectorContainer = new JPanel();
        mediaSelectorContainer.setLayout(new BorderLayout());
        AssetDesigner.paintMainPanel(mediaSelectorContainer);
        mediaContainer.add(mediaSelectorContainer, BorderLayout.CENTER);

        // If this isn't the recommended-container, add left/right buttons.
        if (row > 1)  {
            mediaSelectorContainer.add(makeLeftButton(isSeries), BorderLayout.WEST);
            mediaSelectorContainer.add(makeRightButton(isSeries), BorderLayout.EAST);
        }

        // Add media to the center of the mediaSelectorContainer.
        JPanel mediaPosterIcons = new JPanel();
        AssetDesigner.paintMainPanel(mediaPosterIcons);
        mediaPosterIcons.setLayout(new FlowLayout(FlowLayout.CENTER));
        mediaSelectorContainer.add(mediaPosterIcons, BorderLayout.CENTER);
        if (row == 1) {
            recommendedIcons = mediaPosterIcons;
            loadRecommended();
        }
        else if (row == 2) {
            movieIcons = mediaPosterIcons;
            loadMovies();
        }
        else {
            seriesIcons = mediaPosterIcons;
            loadSeries();
        }

        return mediaContainer;
    }

    // This method loads the 3 recommended movies chosen by 'redaktionen',
    // and returns them as a JButton ArrayList.
    private void loadRecommended() {
        ArrayList<Media> recommended = origin.getController().getRecommended();
        addMedia(recommended, recommendedIcons);
    }

    // This method loads the (field) mediaPerRow currently viewing movies based on movieIndex,
    // and adds them to the movieIcons container.
    private void loadMovies() {
        ArrayList<Media> showThisTime = new ArrayList<>();

        // Loops through the movie database and loads (field) mediaPerRow of them as JButton.
        for(int i = movieIndex; i < movieIndex + mediaPerRow; i++) showThisTime.add(origin.getController().getMovies().get(i));

        addMedia(showThisTime, movieIcons);
    }

    // This method loads the (field) mediaPerRow currently viewing series based on seriesIndex,
    // and returns them as a JButton ArrayList.
    private void loadSeries() {
        ArrayList<Media> showThisTime = new ArrayList<>();

        // Loops through the series database and loads (field) mediaPerRow of them as JButton.
        for(int i = seriesIndex; i < seriesIndex + mediaPerRow; i++) showThisTime.add(origin.getController().getSeries().get(i));

        addMedia(showThisTime, seriesIcons);
    }

    private void addMedia(ArrayList<Media> media, Container container) {
        for(Media m : media) {
            JButton icon = new JButton(new ImageIcon(m.getPoster().getScaledInstance((int) (140*origin.getScaling()),(int)(209*origin.getScaling()), BufferedImage.SCALE_DEFAULT)));
            allButtons.add(icon);

            icon.setText(m.getTitle());
            AssetDesigner.paintMediaButton(icon);

            icon.addActionListener(l -> MediaInfoWindow.showMediaInfo(m, this));
            container.add(icon);
        }
    }

    private JButton makeLeftButton(boolean isSeries) {
        JButton leftButton = new JButton();
        AssetDesigner.paintArrowButtons(leftButton);
        try {
            leftButton.setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("buttons/buttons_left.png"))));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        allButtons.add(leftButton);

        if (isSeries) {
            leftButton.addActionListener(l -> {
                if(seriesIndex >= mediaPerRow && seriesIndex <= 99) {
                    seriesIndex -= mediaPerRow;
                    seriesIcons.removeAll();
                    loadSeries();
                    origin.pack();
                } else if(seriesIndex < mediaPerRow) {
                    seriesIndex = 95;
                    seriesIcons.removeAll();
                    loadSeries();
                    origin.pack();
                }
            });
        } else {
            leftButton.addActionListener(l -> {
                if(movieIndex >= mediaPerRow && movieIndex <= origin.getController().getMovies().size() - mediaPerRow) {
                    movieIndex -= mediaPerRow;
                    movieIcons.removeAll();
                    loadMovies();
                    origin.pack();
                } else if(movieIndex < mediaPerRow) {
                    movieIndex = 95;
                    movieIcons.removeAll();
                    loadMovies();
                    origin.pack();
                }
            });
        }

        return leftButton;
    }

    private JButton makeRightButton(boolean isSeries) {
        JButton rightButton = new JButton();
        AssetDesigner.paintArrowButtons(rightButton);
        try {
            rightButton.setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("buttons/buttons_right.png"))));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        allButtons.add(rightButton);
        if (isSeries) {
            rightButton.addActionListener(l -> {
                if(seriesIndex >= 0 && seriesIndex < origin.getController().getMovies().size() - mediaPerRow) {
                    seriesIndex += mediaPerRow;
                    seriesIcons.removeAll();
                    loadSeries();
                    origin.pack();
                } else if(seriesIndex >= origin.getController().getMovies().size() - mediaPerRow) {
                    seriesIndex = 0;
                    seriesIcons.removeAll();
                    loadSeries();
                    origin.pack();
                }
            });
        } else {
            rightButton.addActionListener(l -> {
                if(movieIndex >= 0 && movieIndex < origin.getController().getMovies().size() - mediaPerRow) {
                    movieIndex += mediaPerRow;
                    movieIcons.removeAll();
                    loadMovies();
                    origin.pack();
                } else if(movieIndex >= 95) {
                    movieIndex = 0;
                    movieIcons.removeAll();
                    loadMovies();
                    origin.pack();
                }
            });
        }

        return rightButton;
    }
}