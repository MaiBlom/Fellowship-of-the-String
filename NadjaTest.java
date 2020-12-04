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

public class NadjaTest {
    public static void main(String[] args) throws FileNotFoundException {
        MediaDB db = MediaDB.getInstance();
        MediaReader fr = MediaReader.getInstance(db);
        fr.readAll();

        ArrayList<Media> liste = db.getSeries();
        
        new MediaInfoWindow(liste.get(16));
    }
}
