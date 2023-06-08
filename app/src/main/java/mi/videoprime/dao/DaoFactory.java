package mi.videoprime.dao;

import android.content.Context;

public class DaoFactory {

    private static UserDao userDao;

    private static FavoriteDAO favoriteDAO;

    private static MovieDAO movieDAO;

    private DaoFactory() {}

    public static UserDao getUserDao(Context context) {
        if (userDao == null)
            userDao = new SQLiteUserDao(context);
        return userDao;
    }

    public static FavoriteDAO getFavoriteDAO(Context context){
        if (favoriteDAO == null)
            favoriteDAO = new SQLiteFavoriteDAO(context);
        return favoriteDAO;
    }

    public static MovieDAO getMovieDAO(Context context){
        if (movieDAO == null)
            movieDAO = new SQLiteMovieDAO(context);
        return movieDAO;
    }

}
