package Controller;
import GUI.*;

public class Main {
    private final GUI gui;

    public static void main(String[] args) {
        new Main();
    }

    private Main() {
        MediaController.getInstance();
        gui = GUI.getInstance();
    }

    public GUI getGui() { return gui; }
}
