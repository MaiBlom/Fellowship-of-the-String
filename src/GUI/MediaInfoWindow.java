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
import java.io.InputStream;

public class MediaInfoWindow extends JInternalFrame {
    Media media;
    Container contentPane;
    Container eastContainer;
    Container centerContainer;
    Container mediaInfo;
    Container seriesSeasons;

    public MediaInfoWindow(Media media) {
        super(media.getTitle(), false, true);
        this.media = media;
        setup();
    }

    private void setup() {
        contentPane = super.getContentPane();
        contentPane.setLayout(new GridLayout());

        setupEastContainer();
        setupCenterContainer();
    }

    private void setupEastContainer() {
        eastContainer = new Container();
        eastContainer.setLayout(new FlowLayout());

        ImageIcon image = new ImageIcon();
        image.setImage(media.getPoster());
        JLabel mediaPosterLabel = new JLabel();
        mediaPosterLabel.setIcon(image);
        contentPane.add(mediaPosterLabel);
        eastContainer.add(mediaPosterLabel);

        contentPane.add(eastContainer, BorderLayout.EAST);
    }

    private void setupCenterContainer() {
        centerContainer = new Container();
        centerContainer.setLayout(new BorderLayout());
        contentPane.add(centerContainer, BorderLayout.CENTER);

        setupMediaInfo();
    }

    private void setupMediaInfo() {
        mediaInfo = new Container();
        mediaInfo.setLayout(new GridLayout(1,4));

        JLabel title = new JLabel(media.getTitle());
        mediaInfo.add(title);

        Container ratingContainer = new Container();
        double ratingVal = media.getRating();
        JLabel rating = new JLabel(Double.toString(ratingVal));
        ratingContainer.setLayout(new FlowLayout());
        BufferedImage starImage;
        try {
            starImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/serieforsider/The Godfather.jpg"));
        } catch (IOException e) {
            starImage = null;
        }
        JLabel stars = new JLabel();
        stars.setIcon(new ImageIcon(starImage.getSubimage(0, 0, (int) (starImage.getWidth()*(ratingVal/10)), starImage.getHeight())));
        ratingContainer.add(stars);
        ratingContainer.add(rating);
        mediaInfo.add(ratingContainer);

        JLabel release = new JLabel();
        if (media instanceof Movie) release.setText(Integer.toString(media.getRelease()));
        else {
            Series s = (Series) media;
            release.setText(s.getRuntime());
        }
        mediaInfo.add(release);

        StringBuilder genreBuilder = new StringBuilder("Genres: ");
        String[] mediaGenres = media.getGenres();
        for (int i = 0; i<mediaGenres.length; i++) {
            genreBuilder.append(mediaGenres[i]);
            if (i != mediaGenres.length-1) genreBuilder.append(", ");
        }
        JLabel genres = new JLabel(genreBuilder.toString());
        mediaInfo.add(genres);
    }
}
