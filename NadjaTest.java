import src.Media.*;
import src.*;
import src.GUI.*;

import java.util.*;
import java.util.Locale;
import java.util.Scanner;
import java.util.HashMap;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

public class NadjaTest {
    public static void main(String[] args) throws FileNotFoundException {
        MediaDB db = MediaDB.getInstance();
        MediaReader fr = MediaReader.getInstance(db);
        fr.readAll();

        ArrayList<Media> liste = db.getSeries();
        testSearchResult();

    }

    private static void testLoginPopUp() {
        JFrame gui = new JFrame();
        Container c = gui.getContentPane();
        c.add(new LoginPopUp(gui));
        gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gui.pack();
        gui.setVisible(true);
    }

    private static void testPlayMediaPage(Media m) {
        JFrame gui = new JFrame();
        Container c = gui.getContentPane();
        c.add(new PlayMediaPage(m));
        gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gui.pack();
        gui.setVisible(true);
    }

    private static void testSearchResult() {
        JFrame gui = new JFrame();
        Container c = gui.getContentPane();
        c.add(new SearchResult("", true, false, new boolean[] {true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}, new User("Frodo"), gui));
        gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gui.pack();
        gui.setVisible(true);
    }
}
