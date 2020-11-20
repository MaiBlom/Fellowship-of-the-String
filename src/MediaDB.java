package src;

import java.util.Hashtable;

public class MediaDB {
    Hashtable<String, Media> allMedia;

    public MediaDB() {
        allMedia = new Hashtable<>();
    }

    public void add(Media m) {
        allMedia.put(m.getTitle(), m);
    }

    public Media get(String title) {
        return allMedia.get(title);
    }
}
