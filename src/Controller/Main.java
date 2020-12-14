package Controller;
import GUI.*;

public class Main {
    private final GUI gui;
    private final MediaController controller;

    public static void main(String[] args) {
        new Main();
    }

    private Main() {
        controller = MediaController.getInstance();
        gui = GUI.getInstance();
    }

    public GUI getGui() { return gui; }
}
