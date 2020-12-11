package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class AssetDesigner {
    public static Color textColor = new Color(255,255,255);
    public static Font mediaFont = new Font("Arial", Font.PLAIN, 11);
    public static Font clickableButtonFont = new Font("Verdana", Font.BOLD, 15);
    public static Font headerFont = new Font("Verdana", Font.BOLD, 15);
    public static Font mediaInfoFont = new Font("Verdana", Font.BOLD, 14);
    public static Font comboBoxFont = new Font("Verdana", Font.BOLD, 15);
    public static Font checkBoxFont = new Font("Verdana", Font.BOLD, 14);

    public static Font buttonTitles = new Font("Verdana", Font.BOLD, 11);
    public static Font loginScreenFont = new Font("Verdana", Font.PLAIN, 13);
    public static Color mainColor = new Color(45, 53, 64);
    public static Color mediaButtonBorder = new Color(58,77,104);
    public static Color buttonColor = new Color(17, 19, 21);
    public static Color accentColor = new Color(37, 43, 52);


    public static void paintMediaButton(JButton b){
        b.setForeground(textColor);
        b.setFont(buttonTitles);
        b.setBorder(new LineBorder(mediaButtonBorder,4,true));
        b.setBackground(mainColor);
        b.setVerticalTextPosition(SwingConstants.BOTTOM);
        b.setHorizontalTextPosition(SwingConstants.CENTER);
        b.setPreferredSize(new Dimension(150,250));
    }

    public static void paintMainPanel(JPanel p) {
        p.setBackground(mainColor);
        p.setBorder(null);
    }

    public static void paintCheckBox(JCheckBox c){
        c.setForeground(textColor);
        c.setFont(checkBoxFont);
        c.setBackground(mainColor);
    }

    public static void paintComboBox(JComboBox c){
        c.setForeground(textColor);
        c.setFont(comboBoxFont);
        c.setBackground(mainColor);
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