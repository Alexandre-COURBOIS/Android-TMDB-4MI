package mi.videoprime.dao;

import java.util.List;

import mi.videoprime.model.Favorite;
import mi.videoprime.model.User;

public interface FavoriteDAO {
    public List<Favorite> getAll();
    public int add(Favorite favorite);
    boolean remove(long movieId);
    public Favorite get(long id);
}
