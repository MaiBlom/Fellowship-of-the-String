package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

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

    public static void paintAccentPanel(JPanel a) {
        a.setBorder(null);
        a.setBackground(accentColor);
    }

    public static void paintCheckBox(JCheckBox c){
        c.setForeground(textColor);
        c.setFont(checkBoxFont);
        c.setBackground(mainColor);
    }

    public static void paintComboBox(JComboBox<String> c){
        c.setForeground(textColor);
        c.setFont(comboBoxFont);
        c.setBackground(mainColor);
    }

    public static void paintClickableButton(JButton c) {
        c.setForeground(textColor);
        c.setFont(clickableButtonFont);
        c.setPreferredSize(new Dimension(150, 40));
        c.setBackground(buttonColor);
        c.setBorder(new LineBorder(new Color(44, 60, 77), 1));
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

    public static void paintEpisodeButtons(JButton o) {
        o.setForeground(textColor);
        o.setBackground(buttonColor);
        o.setBorderPainted(false);
       //o.setFont(mediaInfoButtonFont); seems to break the episode buttons
    }

    public static void paintArrowButtons(JButton d) {
        d.setBorderPainted(false);
        d.setBackground(mainColor);
    }

    public static void paintScrollBar(JScrollPane sp) {
        sp.setBorder(null);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        sp.getVerticalScrollBar().setUnitIncrement(30);

        sp.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            protected void configureScrollBarColors() {
                thumbHighlightColor = AssetDesigner.accentColor;
                thumbLightShadowColor = AssetDesigner.accentColor;
                thumbDarkShadowColor = AssetDesigner.accentColor;
                thumbColor = AssetDesigner.accentColor;
                trackColor = AssetDesigner.mainColor;
                trackHighlightColor = AssetDesigner.mainColor;
            }
            protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }
            protected JButton createIncreaseButton(int orientation) { return createZeroButton(); }
            private JButton createZeroButton() {
                JButton jbutton = new JButton();
                jbutton.setPreferredSize(new Dimension(0, 0));
                jbutton.setMinimumSize(new Dimension(0, 0));
                jbutton.setMaximumSize(new Dimension(0, 0));
                return jbutton;
            }
        });
}
}