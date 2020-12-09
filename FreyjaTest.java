// import java.awt.Container;
// import java.util.ArrayList;

// import javax.swing.JFrame;
// import javax.swing.WindowConstants;

// import src.*;
// import src.GUI.Favorites;
// import src.Media.*;
// import src.User;

// //Tests the favorites window.
// public class FreyjaTest {
    
// public static void main(String[] args) {

//     MediaDB db = MediaDB.getInstance();
//         MediaReader fr = MediaReader.getInstance(db);
//         fr.readAll();
//     ArrayList<Media> listeM = db.getMovies();

//     ArrayList<Media> listeS = db.getSeries();
//     User f = new User("Frodo");
//     f.favorite(listeS.get(0));
//     f.favorite(listeS.get(1));
//     f.favorite(listeS.get(2));
//     f.favorite(listeS.get(3));
//     f.favorite(listeM.get(0));
//     f.favorite(listeM.get(1));
//     Favorites hehe = new Favorites(f);

//     JFrame gui = new JFrame();
//         JPanel c = gui.getContentPane();
//        c.add(hehe);
//         gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//         gui.pack();
//         gui.setVisible(true);
//     }
// }
