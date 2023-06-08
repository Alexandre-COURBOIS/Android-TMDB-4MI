package mi.videoprime.service.interfaces;

import java.util.List;

import mi.videoprime.adapters.MoviesAdapter;
import mi.videoprime.model.Movie;

public interface IOfflineDataService {
    void loadOfflineMovies(MoviesAdapter adapter, List<Movie> movies);

}
