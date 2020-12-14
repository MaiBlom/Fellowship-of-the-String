package Controller;

import Model.*;
import GUI.*;

import java.util.ArrayList;

public class Main {
    private static MediaDB mDB;
    private static UserDB uDB;
    private GUI gui;
    private MediaController controller;

    public static void main(String[] args) {
        new Main();
    }

    private Main() {
        mDB = MediaDB.getInstance();
        uDB = UserDB.getInstance();
        controller = MediaController.getInstance();
        setupStartingUsers();

        gui = GUI.getInstance();
    }

    public static MediaDB getmDB() { return mDB; }
    public static UserDB getuDB() { return uDB; }
    public GUI getGui() { return gui; }

    private void setupStartingUsers() {
        ArrayList<Media> movies = mDB.getMovies();
        ArrayList<Media> series = mDB.getSeries();

        controller.createUser("test", new char[] {'t','e','s','t'});
        User test = controller.getUser("test");
        test.favorite(movies.get(1));
        test.favorite(movies.get(9));
        test.favorite(movies.get(63));
        test.favorite(series.get(24));
        test.favorite(series.get(55));
        test.favorite(series.get(73));
    }
}
