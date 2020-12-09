package src.GUI;
import java.awt.*;
import javax.swing.*;

public class TextSettings {
    public static Color textColor = new Color(255,255,255);
    public static Font mediaFont = new Font("Arial", Font.PLAIN, 11);
    public static Font clickableButtonFont = new Font("Verdana", Font.BOLD, 15);
    public static Font mediaLabelFont = new Font("Arial", Font.PLAIN, 15);
    public static Font headerFont = new Font("Verdana", Font.BOLD, 15);
    public static Font loginScreenFont = new Font("Verdana", Font.PLAIN, 13);

    public static void paintMediaFont(JButton b) {
        b.setForeground(textColor);
        b.setFont(mediaFont);
    }

    public static void paintButtonFont(JButton c) {
        c.setForeground(textColor);
        c.setFont(clickableButtonFont);
    }
    
    public static void paintHeader(JLabel l) {
        l.setForeground(textColor);
        l.setFont(headerFont);
    }

    public static void paintLoginScreenFont(JLabel m) {
        m.setForeground(textColor);
        m.setFont(loginScreenFont);
    }
}
