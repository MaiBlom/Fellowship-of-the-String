package src.GUI;

import src.Media.*;

import javax.swing.*;
import java.awt.*;

public class PlayMediaPage extends Container {
    private static final long serialVersionUID = 1L;
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