package GUI.Scenarios;

import GUI.*;
import Model.*;

import javax.swing.*;
import java.awt.*;

public class PlayMediaPage extends Scenario {
    private static final long serialVersionUID = 1L;
    private final Media media;

    // private final int WIDTH = 1000, HEIGHT = 720;

    public PlayMediaPage(Media media) {
        this.media = media;
        setup();
    }

    private void setup() {
        contentPane.setLayout(new FlowLayout());

        JLabel info = new JLabel(
            "<html> <br>You've selected the " + (media instanceof Movie? "movie " : "series ") + media.getTitle() + ". <br>" +
                 "Unfortunately, this " + (media instanceof Movie? "movie " : "series ") + "has been removed due to copyright regulations.<br> </html>"
        );
        AssetDesigner.paintMediaInfoFont(info);
        contentPane.add(info);
    }
}