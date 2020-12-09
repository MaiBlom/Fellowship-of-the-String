package src.GUI;

import src.Media.*;
import src.GUI.PopUps.*;

import javax.swing.*;
import java.awt.*;

public class PlayMediaPage extends JLayeredPane {
    private static final long serialVersionUID = 1L;
    private GUI origin;
    private Media media;
    private JPanel contentPane;

    // private final int WIDTH = 1000, HEIGHT = 720;

    public PlayMediaPage(Media media, GUI origin) {
        this.media = media;
        this.origin = origin;
        setup();
    }

    private void setup() {
        setPreferredSize(new Dimension(origin.getwidth()-12,origin.getheight()-82));

        contentPane = new JPanel();
        ColorTheme.paintMainPanel(contentPane);
        contentPane.setLayout(new FlowLayout());
        contentPane.setBounds(0,0,origin.getwidth()-12,origin.getheight()-82);
        this.add(contentPane, new Integer(0));


        JLabel info = new JLabel(
            "<html> <br>You've selected the " + (media instanceof Movie? "movie " : "series ") + media.getTitle() + ". <br>" +
            "Unfortunately, this " + (media instanceof Movie? "movie " : "series ") + "has been removed due to copyright regulations.<br> </html>"
        );
        TextSettings.paintMediaInfoFont(info);
        contentPane.add(info);
    }
}
