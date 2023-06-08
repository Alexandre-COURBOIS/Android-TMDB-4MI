package mi.videoprime.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mi.videoprime.model.Favorite;
import mi.videoprime.model.User;

public class SQLiteFavoriteDAO extends BaseDaoSqlite implements FavoriteDAO {

    public SQLiteFavoriteDAO(Context context) {
        super(context);
    }

    @Override
    public List<Favorite> getAll() {
        List<Favorite> favorites = new ArrayList<>();
        String[] cols = {Favorite.ID, Favorite.COLUMN_MOVIEID, Favorite.COLUMN_MOVIETITLE};
        Cursor cursor = getDb().query(Favorite.TABLE_NAME, cols, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            favorites.add(new Favorite((int)cursor.getInt(0), cursor.getInt(1), cursor.getString(2)));
            cursor.moveToNext();
        }
        cursor.close();
        getDb().close();
        return favorites;
    }

    @Override
    public int add(Favorite favorite) {
        try {
            ContentValues values = mapFromFavorite(favorite);
            int id = (int) getDb().insert(Favorite.TABLE_NAME, null, values);
            getDb().close();
            return id;
        } catch (SQLiteConstraintException e) {
            Log.e("Database", "error", e);
            return -1;
        }
    }

    @Override
    public boolean remove(long movieId) {
        try {
            String whereClause = Favorite.COLUMN_MOVIEID + "=?";
            String[] whereArgs = new String[] { String.valueOf(movieId) };
            int count = getDb().delete(Favorite.TABLE_NAME, whereClause, whereArgs);
            getDb().close();
            // returns true if deletion was successful, false otherwise
            return count > 0;
        } catch (Exception e) {
            Log.e("Database", "error", e);
            return false;
        }
    }

    @Override
    public Favorite get(long id) {
        Favorite favoriteToGet = null;
        String[] cols = {Favorite.ID, Favorite.COLUMN_MOVIEID, Favorite.COLUMN_MOVIETITLE};
        String[] params = { Long.toString(id) };
        Cursor cursor = getDb().query(Favorite.TABLE_NAME, cols, Favorite.COLUMN_MOVIEID + "=?", params, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {  // meilleur qu'un try catch
            favoriteToGet = new Favorite((int)cursor.getInt(0), cursor.getInt(1), cursor.getString(2));
            cursor.moveToNext();
        }
        cursor.close();
        getDb().close();
        return favoriteToGet;
    }


    private ContentValues mapFromFavorite(Favorite favorite) {
        ContentValues values = new ContentValues();
        values.put(Favorite.COLUMN_MOVIEID, favorite.getMovieId());
        values.put(Favorite.COLUMN_MOVIETITLE, favorite.getMovieTitle());
        return values;
    }
}
