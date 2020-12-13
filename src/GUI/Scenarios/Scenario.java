package GUI.Scenarios;

import Comparators.*;
import GUI.*;
import GUI.PopUps.MediaInfoWindow;
import Misc.*;
import Media.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Scenario extends JLayeredPane implements Clickable {
    protected GUI origin;
    protected ArrayList<JButton> allButtons;
    protected JPanel contentPane;
    protected final MediaDB db;
    protected JPanel sortingContainer;
    protected JScrollPane mediaScrollPane;
    protected JPanel mediaPanel;

    public Scenario() {
        this.origin = origin.getInstance();
        this.allButtons = new ArrayList<>();
        this.contentPane = new JPanel();
        this.db = MediaDB.getInstance();
        setupContentPane();
    }

    public static MainMenu getMainMenu() {
        return new MainMenu();
    }

    private void setupContentPane() {
        this.setPreferredSize(GUI.getCenterDimension());
        this.add(contentPane, new Integer(0));
        AssetDesigner.paintMainPanel(contentPane);
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
        AssetDesigner.paintMainPanel(mediaPanel);
        mediaPanel.setLayout(new FlowLayout(FlowLayout.LEFT,15,0));

        createButtons(results);

        mediaScrollPane = new JScrollPane(mediaPanel);
        AssetDesigner.paintScrollBar(mediaScrollPane);
        contentPane.add(mediaScrollPane,BorderLayout.CENTER);
    }

    protected void makeSorting(ArrayList<Media> results) {
        sortingContainer = new JPanel();
        AssetDesigner.paintMainPanel(sortingContainer);
        sortingContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));

        String[] sortingOptions = {"Sort by...", "Title (A-Z)", "Title (Z-A)", "Release (newest to oldest)",
                                   "Release (oldest to newest)", "Rating (highest to lowest)", "Rating (lowest to highest)"};
        JComboBox<String> sortby = new JComboBox<>(sortingOptions);
        AssetDesigner.paintComboBox(sortby);
        sortby.addActionListener(e -> sort((String) sortby.getSelectedItem(), results));
        sortingContainer.add(sortby);
    }

    private void sort(String s, ArrayList<Media> results) {
        if (s.equals("Title (A-Z)")) {
            results.sort(new TitleCompAZ());
        } else if (s.equals("Title (Z-A)")) {
            results.sort(new TitleCompZA());
        } else if (s.equals("Release (newest to oldest)")) {
            results.sort(new ReleaseCompDecreasing());
        } else if (s.equals("Release (oldest to newest)")) {
            results.sort(new ReleaseCompIncreasing());
        } else if (s.equals("Rating (highest to lowest)")) {
            results.sort(new RatingCompDecreasing());
        } else if (s.equals("Rating (lowest to highest)")) {
            results.sort(new RatingCompIncreasing());
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
            AssetDesigner.paintMediaButton(mediaPoster);
            AssetDesigner.paintMediaButton(mediaPoster);

            mediaPoster.addActionListener(l -> MediaInfoWindow.showMediaInfo(m, this));
            mediaPanel.add(mediaPoster);
        }

        int numberOfResults = results.size();
        mediaPanel.setPreferredSize(new Dimension(6*150,(numberOfResults/6 + (numberOfResults % 6 == 0? 0:1))*250));
        origin.pack();
    }
}
