package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ColorTheme {
    public static Color buttonColor = new Color(17, 19, 21);
    public static Color mainColor = new Color(45, 53, 64);
    public static Color accentColor = new Color(37, 43, 52);
    public static Color textColor = new Color(255, 255, 255);


    public static void paintMediaButton(JButton b) {
        b.setBorder(new LineBorder(new Color(58,77,104),4,true));
        b.setBackground(mainColor);
    }

    public static void paintClickableButton(JButton c) { 
        c.setPreferredSize(new Dimension(150,40));
        c.setBackground(buttonColor);
        c.setBorder(new LineBorder(new Color(44 , 60, 77),1));
    }

    public static void paintMediaInfoButtons(JButton e) {
        e.setBackground(buttonColor);
        e.setBorderPainted(false);
    } 

    public static void paintArrowButtons(JButton d) {
        d.setBorderPainted(false);
        d.setBackground(mainColor);
    }

    public static void paintMainPanel(JPanel p) {
        p.setBackground(mainColor);
        p.setBorder(null);
    }

    public static void paintAccentPanel(JPanel a) {
        a.setBorder(null);
        a.setBackground(accentColor);
    }

    public static void paintScrollBar(JScrollPane sp) {
        sp.setBorder(null);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        sp.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            protected void configureScrollBarColors() {
                thumbHighlightColor = ColorTheme.accentColor;
                thumbLightShadowColor = ColorTheme.accentColor;
                thumbDarkShadowColor = ColorTheme.accentColor;
                thumbColor = ColorTheme.accentColor;
                trackColor = ColorTheme.mainColor;
                trackHighlightColor = ColorTheme.mainColor;
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