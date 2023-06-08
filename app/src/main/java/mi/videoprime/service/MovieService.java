package mi.videoprime.service;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import mi.videoprime.dao.DaoFactory;
import mi.videoprime.model.Favorite;
import mi.videoprime.model.Movie;
import mi.videoprime.service.interfaces.IFavoriteService;
import mi.videoprime.service.interfaces.IMovieService;
import mi.videoprime.service.interfaces.IToastService;

public class MovieService implements IMovieService {

    Context _context;

    @Inject
    IToastService _toastService;

    @Inject
    public MovieService(@ApplicationContext Context context) {
        _context = context;
    }


    @Override
    public List<Movie> getAll() {

        List<Movie> movies = DaoFactory.getMovieDAO(_context).getAll();

        if (movies.size() == 0) {
            _toastService.showToastError("Mode hors connexion - Reconnectez-vous à internet",3000);
        }
        return movies;
    }

    @Override
    public Movie getById(long id) {
        return DaoFactory.getMovieDAO(_context).get(id);
    }

    @Override
    public Movie createMovie(Movie movie) {

        int movieId = DaoFactory.getMovieDAO(_context).add(movie);

        if (movieId >= 1) {
            return movie;
        }

        return null;
    }

}
