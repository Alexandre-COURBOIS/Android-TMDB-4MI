package mi.videoprime.dao;

import java.util.List;

import mi.videoprime.model.Favorite;
import mi.videoprime.model.Movie;

public interface MovieDAO {
    public List<Movie> getAll();
    public int add(Movie movie);
    boolean remove(int movieId);
    public Movie get(long id);
}
