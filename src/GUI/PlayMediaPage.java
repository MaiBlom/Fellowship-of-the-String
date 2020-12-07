package src.GUI;

import src.MediaDB;
import src.Media.*;
import src.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;

public class PlayMediaPage extends Container {
    private Media media;

    public PlayMediaPage(Media media) {
        this.media = media;
        this.setLayout(new FlowLayout());
        setup();
    }

    private void setup() {
        JLabel info = new JLabel(
            "<html> <br>You've selected the " + (media instanceof Movie? "movie " : "series ") + media.getTitle() + ". <br>" +
            "Unfortunately, this " + (media instanceof Movie? "movie " : "series ") + "has been removed due to copyright regulations.<br> </html>"
        );
        this.add(info);
    }
}
