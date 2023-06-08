package mi.videoprime.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import mi.videoprime.adapters.MoviesAdapter;
import mi.videoprime.model.Movie;
import mi.videoprime.service.interfaces.IMovieService;
import mi.videoprime.service.interfaces.IOfflineDataService;

public class OfflineDataService implements IOfflineDataService {
    Context _context;

    @Inject
    public OfflineDataService(@ApplicationContext Context context) {
        _context = context;
    }

    @Override
    public void loadOfflineMovies(MoviesAdapter adapter,List<Movie> movies) {

        if (adapter.getItemCount() > 0){
            Log.i("MoviesFragment", "Movies has already been loaded");
            return;
        }

        if (movies.size() > 0 && adapter.getItemCount() == 0){
            adapter.addMovies(movies);
            return;
        }

        Log.i("MoviesFragment", "Unable to found movies in DB");

    }

}
