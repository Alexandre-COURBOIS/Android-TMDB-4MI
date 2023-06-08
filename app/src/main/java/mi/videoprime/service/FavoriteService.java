package mi.videoprime.service;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import mi.videoprime.dao.DaoFactory;
import mi.videoprime.model.Favorite;
import mi.videoprime.model.Movie;
import mi.videoprime.model.User;
import mi.videoprime.service.interfaces.IFavoriteService;
import mi.videoprime.service.interfaces.IToastService;

public class FavoriteService implements IFavoriteService {

    Context _context;
    @Inject
    IToastService _toastService;

    @Inject
    public FavoriteService(@ApplicationContext Context context) {
        _context = context;
    }


    @Override
    public List<Favorite> getAll() {
        return DaoFactory.getFavoriteDAO(_context).getAll();
    }

    @Override
    public Favorite createFavorite(Favorite favorite) {
        int favoriteId =  DaoFactory.getFavoriteDAO(_context).add(favorite);

        if (favoriteId >= 1) {
            return favorite;
        } else {
            deleteFavorite(favorite);
            return null;
        }
    }

    public void deleteById(int id){
        DaoFactory.getFavoriteDAO(_context).remove(id);
    }

    @Override
    public Boolean deleteFavorite(Favorite favorite) {
        return DaoFactory.getFavoriteDAO(_context).remove(favorite.getMovieId());
    }

    @Override
    public Boolean isFavorite(Movie movie) {
        Favorite getFavorite = DaoFactory.getFavoriteDAO(_context).get(movie.getId());
        return getFavorite != null;
    }



}
