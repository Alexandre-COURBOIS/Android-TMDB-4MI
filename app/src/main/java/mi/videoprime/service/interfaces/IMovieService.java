package mi.videoprime.service.interfaces;

import java.util.List;

import mi.videoprime.model.Favorite;
import mi.videoprime.model.Movie;

public interface IMovieService {
    List<Movie> getAll();
    Movie createMovie(Movie movie);

    Movie getById(long id);
}
