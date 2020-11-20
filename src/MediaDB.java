package src;

import src.Media.*;

import java.util.HashMap;

public class MediaDB {
    HashMap<String, Media> allMedia;

    public MediaDB() {
        allMedia = new HashMap<>();
    }

    public void add(Media m) {
        allMedia.put(m.getTitle(), m);
    }

    public Media get(String title) {
        return allMedia.get(title);
    }

    public HashMap<String, Media> getHM() {
        return allMedia;
    }
}
