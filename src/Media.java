package src;

public abstract class Media {
    protected String title;
    protected int release;
    protected int rating; 

    public Media(String title, int release, int rating) {
        this.title = title;
        this.release = release;
        this.rating = rating;
    }

    public String getTitle(){
        return title;
    }

    public int getRelease(){
        return release; //smile
    }
}
