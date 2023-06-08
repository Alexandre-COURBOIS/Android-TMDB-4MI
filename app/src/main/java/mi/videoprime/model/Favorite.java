package mi.videoprime.model;

import java.io.Serializable;

import mi.videoprime.mapping.FavoriteMapping;

public class Favorite implements Serializable, FavoriteMapping {

    private int id;
    private long movieId;
    private String movieTitle;

    public Favorite(int id, long movieId, String movieTitle)
    {
        this.id = id;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
    }

    public Favorite() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
}
