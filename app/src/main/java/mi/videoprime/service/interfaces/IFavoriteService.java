package mi.videoprime.service.interfaces;

import java.util.List;

import mi.videoprime.model.Favorite;
import mi.videoprime.model.Movie;
import mi.videoprime.model.User;

public interface IFavoriteService {
    List<Favorite> getAll();
    Favorite createFavorite(Favorite favorite);
    Boolean deleteFavorite(Favorite favorite);
    Boolean isFavorite(Movie movie);

    void deleteById(int id);
}
