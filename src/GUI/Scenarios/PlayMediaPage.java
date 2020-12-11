package GUI.Scenarios;

import GUI.*;
import Media.*;
import Misc.*;

import javax.swing.*;
import java.awt.*;

public class PlayMediaPage extends Scenario {
    private static final long serialVersionUID = 1L;
    private Media media;

    // private final int WIDTH = 1000, HEIGHT = 720;

    public PlayMediaPage(GUI origin, User currentUser, Media media) {
        super(origin, currentUser);
        this.media = media;

        setup();
    }

    private void setup() {
        contentPane.setLayout(new FlowLayout());

        JLabel info = new JLabel(
            "<html> <br>You've selected the " + (media instanceof Movie? "movie " : "series ") + media.getTitle() + ". <br>" +
            "Unfortunately, this " + (media instanceof Movie? "movie " : "series ") + "has been removed due to copyright regulations.<br> </html>"
        );
        TextSettings.paintMediaInfoFont(info);
        contentPane.add(info);
    }
}