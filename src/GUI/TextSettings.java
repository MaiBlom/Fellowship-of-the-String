package GUI;

import java.awt.*;
import javax.swing.*;

public class TextSettings {
    public static Color textColor = new Color(255,255,255);
    public static Font mediaFont = new Font("Arial", Font.PLAIN, 11);
    public static Font clickableButtonFont = new Font("Verdana", Font.BOLD, 15);
    public static Font mediaLabelFont = new Font("Arial", Font.PLAIN, 15);
    public static Font headerFont = new Font("Verdana", Font.BOLD, 15);
    public static Font loginScreenFont = new Font("Verdana", Font.PLAIN, 13);
    public static Font mediaInfoFont = new Font("Verdana", Font.BOLD, 14);
    public static Font mediaInfoButtonFont = new Font("Verdana", Font.BOLD, 11);
    public static Font comboBoxFont = new Font("Verdana", Font.BOLD, 15);
    public static Font checkBoxFont = new Font("Verdana", Font.BOLD, 14);

    public static Font buttonTitles = new Font("Verdana", Font.BOLD, 11);

    public static void paintMediaButton(JButton b){
        b.setForeground(textColor);
        b.setFont(buttonTitles);
        b.setVerticalTextPosition(SwingConstants.BOTTOM);
        b.setHorizontalTextPosition(SwingConstants.CENTER);
        b.setPreferredSize(new Dimension(150,250));
    }
    public static void paintMediaFont(JButton b) {
        b.setForeground(textColor);
        b.setFont(mediaFont);
    }

    public static void paintCheckBoxFont(JCheckBox c){
        c.setForeground(textColor);
        c.setFont(checkBoxFont);
    }

    public static void paintComboBoxFont(JComboBox c){
        c.setForeground(textColor);
        c.setFont(comboBoxFont);
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

    public static void paintMediaInfoFont(JLabel n) {
        n.setForeground(textColor);
        n.setFont(mediaInfoFont);
    }

    public static void paintMediaInfoButtons(JButton o) {
        o.setForeground(textColor);
       //o.setFont(mediaInfoButtonFont);
    }
}