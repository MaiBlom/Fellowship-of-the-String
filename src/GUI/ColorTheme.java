package src.GUI;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class ColorTheme {
    public static Color buttonColor = new Color(17, 19, 21);
    public static Color mainColor = new Color(45, 53, 64);
    public static Color accentColor = new Color(37, 43, 52);
    public static Color textColor = new Color(255, 255, 255);


    public static void paintMediaButton(JButton b) {
        b.setBorder(new LineBorder(new Color(58,77,104),4,true));
        b.setBackground(ColorTheme.mainColor);
    }

    public static void paintClickableButton(JButton c) { 
        c.setPreferredSize(new Dimension(150,40));
        c.setBackground(ColorTheme.buttonColor);
        c.setBorder(new LineBorder(new Color(44 , 60, 77),1));
    }

    public static void paintMediaInfoButtons(JButton e) {
        e.setBackground(ColorTheme.buttonColor);
        e.setBorderPainted(false);
    } 

    public static void paintArrowButtons(JButton d) {
        d.setBorderPainted(false);
        d.setBackground(ColorTheme.mainColor);
    }

    public static void paintMainPanel(JPanel p) {
        p.setBackground(ColorTheme.mainColor);
    }

    public static void paintAccentPanel(JPanel a) {
        a.setBackground(ColorTheme.accentColor);
    }
}
