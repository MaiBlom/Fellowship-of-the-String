package src.GUI;

import src.MediaDB;
import src.Media.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileReader;
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
        frame.setLocationRelativeTo(null);
        contentPane = frame.getContentPane();
        contentPane.setLayout(new GridLayout(2,1));


        frame.pack();
        frame.setVisible(true);
    }


    private void setup() {
        db = MediaDB.getInstance();
        FileReader fr = FileReader.getInstance(db);
        fr.readAll();
    }
}
