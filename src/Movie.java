public class Movie {

    private String title;
    private String poster;
    private double popularity;

    public Movie(String title, String poster, double popularity) {
        this.title = title;
        this.poster = poster;
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }
}
