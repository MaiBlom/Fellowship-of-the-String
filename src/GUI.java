package src;

import src.Media.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// This GUI is WIP, we use it for testing for now.

public class GUI {
    private MediaDB db;
    private JFrame frame;
    private Container contentPane;
    
    public static void main(String[] args) {
        new GUI();
    }

    public GUI() {
        makeFrame();
    }

    private void makeFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contentPane = frame.getContentPane();
        contentPane.setLayout(new GridLayout(5,5));
        setup();

        HashMap<String, Media> allMedia = db.getAllMedia();
        Media godfather = allMedia.get("The Godfather");
        
        ImageIcon image = new ImageIcon();
        image.setImage(godfather.getPoster());

        JLabel label = new JLabel();
        label.setIcon(image);
        contentPane.add(label);

        frame.pack();
        frame.setVisible(true);
    }


    private void setup() {
        db = MediaDB.getInstance();
        FileReader fr = FileReader.getInstance(db);
        fr.readAll();
    }
}
