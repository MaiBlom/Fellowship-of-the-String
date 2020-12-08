import src.*;

import java.io.FileNotFoundException;

public class NadjaTest {
    public static void main(String[] args) throws FileNotFoundException {
        MediaDB db = MediaDB.getInstance();
        MediaReader fr = MediaReader.getInstance(db);
        fr.readAll();
    }
}
