package src.GUI;

import src.*;
import src.Media.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MediaInfoWindow extends JInternalFrame {
    Media media;
    Container contentPane;
    Container leftContainer;
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
        Container eastContainer = new Container();
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
        Container centerContainer = new Container();
        centerContainer.setLayout(new BorderLayout());
        contentPane.add(centerContainer, BorderLayout.CENTER);
    }
}
