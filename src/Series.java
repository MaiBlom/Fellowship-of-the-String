package src;
public class Series extends Media {
    private int[][] seasons;

    public Series(String title, int release, int rating, int[][] seasons) {
        super(title, release, rating);
        this.seasons = seasons;
    }

    public int[][] getSeason() {
        return seasons;
    }
}
