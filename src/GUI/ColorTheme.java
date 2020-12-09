package src.GUI;

import java.awt.*;

public class ColorTheme {
    public static Color buttonColor;
    public static Color mainColor;
    public static Color accentColor;
    
    public ColorTheme(Color buttonColor, Color mainColor, Color accentColor){
        ColorTheme.buttonColor = new Color(45,53,64);
        ColorTheme.mainColor = new Color(22,25,28);
        ColorTheme.accentColor = new Color(37, 43, 52);
    }
}
