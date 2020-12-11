package GUI.Scenarios;

import Comparators.*;
import GUI.*;
import GUI.PopUps.MediaInfoWindow;
import Misc.*;
import Media.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Scenario extends JLayeredPane implements Clickable {
    protected GUI origin;
    protected User currentUser;
    protected ArrayList<JButton> allButtons;
    protected JPanel contentPane;
    protected JPanel sortingContainer;
    protected JScrollPane mediaScrollPane;
    protected JPanel mediaPanel;

    public Scenario(GUI origin, User currentUser) {
        this.origin = origin;
        this.currentUser = currentUser;
        this.allButtons = new ArrayList<>();
        this.contentPane = new JPanel();
        setupContentPane();
    }

    private void setupContentPane() {
        this.setPreferredSize(origin.getCenterDimension());
        this.add(contentPane, new Integer(0));
        ColorTheme.paintMainPanel(contentPane);
        contentPane.setBounds(origin.getCenterBounds());
    }

    public void buttonsSetEnabled(boolean b) {
        for (JButton x : allButtons) {
            x.setEnabled(b);
        }
    }

    // The results-container has a FlowLayout to add all media in the results-arraylist. A JScrollPane makes sure that we're able
    // to scroll through the results if there are more than what is shown on screen. The images of the media are put on buttons
    // which each call the showMediaInfo() method.
    protected void makeMediaContainer(ArrayList<Media> results){
        mediaPanel = new JPanel();
        ColorTheme.paintMainPanel(mediaPanel);
        mediaPanel.setLayout(new FlowLayout(FlowLayout.LEFT,15,0));

        createButtons(results);

        mediaScrollPane = new JScrollPane(mediaPanel);
        ColorTheme.paintScrollBar(mediaScrollPane);
        contentPane.add(mediaScrollPane,BorderLayout.CENTER);
    }

    protected void makeSorting(ArrayList<Media> results) {
        sortingContainer = new JPanel();
        ColorTheme.paintMainPanel(sortingContainer);
        sortingContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));

        String[] sortingOptions = {"Sort by...", "Title (A-Z)", "Title (Z-A)", "Release (newest to oldest)",
                                   "Release (oldest to newest)", "Rating (highest to lowest)", "Rating (lowest to highest)"};
        JComboBox<String> sortby = new JComboBox<>(sortingOptions);
        ColorTheme.paintComboBox(sortby);
        TextSettings.paintComboBoxFont(sortby);
        sortby.addActionListener(e -> sort((String) sortby.getSelectedItem(), results));
        sortingContainer.add(sortby);
    }

    private void sort(String s, ArrayList<Media> results) {
        if (s.equals("Title (A-Z)")) {
            Collections.sort(results, new TitleCompAZ());
        } else if (s.equals("Title (Z-A)")) {
            Collections.sort(results, new TitleCompZA());
        } else if (s.equals("Release (newest to oldest)")) {
            Collections.sort(results, new ReleaseCompDecreasing());
        } else if (s.equals("Release (oldest to newest)")) {
            Collections.sort(results, new ReleaseCompIncreasing());
        } else if (s.equals("Rating (highest to lowest)")) {
            Collections.sort(results, new RatingCompDecreasing());
        } else if (s.equals("Rating (lowest to highest)")) {
            Collections.sort(results, new RatingCompIncreasing());
        }
        mediaPanel.removeAll();
        allButtons.clear();
        createButtons(results);
        setPreferredSize(origin.getCenterDimension());
        contentPane.setBounds(origin.getCenterBounds());
        origin.pack();
    }

    private void createButtons(ArrayList<Media> results) {
        for (Media m : results) {
            JButton mediaPoster = new JButton(new ImageIcon(m.getPoster()));
            allButtons.add(mediaPoster);

            mediaPoster.setText(m.getTitle());
            TextSettings.paintMediaButton(mediaPoster);
            ColorTheme.paintMediaButton(mediaPoster);

            mediaPoster.addActionListener(l -> MediaInfoWindow.showMediaInfo(m, origin, currentUser, this));
            mediaPanel.add(mediaPoster);
        }

        int numberOfResults = results.size();
        mediaPanel.setPreferredSize(new Dimension(6*150,(numberOfResults/6 + (numberOfResults % 6 == 0? 0:1))*250));
        origin.pack();
    }
}
