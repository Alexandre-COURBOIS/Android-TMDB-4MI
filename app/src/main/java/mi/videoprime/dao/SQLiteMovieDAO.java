package mi.videoprime.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import mi.videoprime.model.Movie;

public class SQLiteMovieDAO extends BaseDaoSqlite implements MovieDAO {

    public SQLiteMovieDAO(Context context) {
        super(context);
    }

    @Override
    public List<Movie> getAll() {
        List<Movie> movies = new ArrayList<>();
        String[] cols = {Movie.ID, Movie.COLUMN_TITLE, Movie.COLUMN_OVERVIEW,
                Movie.COLUMN_POSTERPATH,Movie.COLUMN_BACKDROPPATH,Movie.COLUMN_VOTEAVERAGE,
                Movie.COLUMN_RELEASEDATE,Movie.COLUMN_MOVIE_ID};

        Cursor cursor = getDb().query(Movie.TABLE_NAME, cols, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            movies.add(new Movie(cursor.getLong(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3),cursor.getString(4),cursor.getDouble(5),
                    cursor.getString(6),cursor.getLong(7)));
            cursor.moveToNext();
        }
        cursor.close();
        getDb().close();
        return movies;
    }

    @Override
    public int add(Movie movie) {
        try {
            ContentValues values = mapFromMovie(movie);
            int id = (int) getDb().insert(Movie.TABLE_NAME, null, values);
            getDb().close();
            return id;
        } catch (SQLiteConstraintException e) {
            Log.e("Database", "error", e);
            return -1;
        }
    }

    @Override
    public boolean remove(int movieId) {
        try {
            String whereClause = Movie.ID + "=?";
            String[] whereArgs = new String[] { String.valueOf(movieId) };
            int count = getDb().delete(Movie.TABLE_NAME, whereClause, whereArgs);
            getDb().close();
            // returns true if deletion was successful, false otherwise
            return count > 0;
        } catch (Exception e) {
            Log.e("Database", "error", e);
            return false;
        }
    }

    @Override
    public Movie get(long id) {
        Movie movieToGet = null;
        String[] cols = {Movie.ID, Movie.COLUMN_TITLE, Movie.COLUMN_OVERVIEW,
                Movie.COLUMN_POSTERPATH,Movie.COLUMN_BACKDROPPATH,Movie.COLUMN_VOTEAVERAGE,
                Movie.COLUMN_RELEASEDATE,Movie.COLUMN_MOVIE_ID};
        String[] params = { Long.toString(id) };
        Cursor cursor = getDb().query(Movie.TABLE_NAME, cols, Movie.COLUMN_MOVIE_ID + "=?", params, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {  // meilleur qu'un try catch
            movieToGet =new Movie(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3),cursor.getString(4),cursor.getDouble(5),
                    cursor.getString(6),cursor.getLong(7));
            cursor.moveToNext();
        }
        cursor.close();
        getDb().close();
        return movieToGet;
    }


    private ContentValues mapFromMovie(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(Movie.COLUMN_TITLE, movie.getTitle());
        values.put(Movie.COLUMN_OVERVIEW, movie.getOverview());
        values.put(Movie.COLUMN_POSTERPATH, movie.getPosterPath());
        values.put(Movie.COLUMN_BACKDROPPATH, movie.getBackdropPath());
        values.put(Movie.COLUMN_VOTEAVERAGE, movie.getVoteAverage());
        values.put(Movie.COLUMN_RELEASEDATE, movie.getReleaseDate());
        values.put(Movie.COLUMN_MOVIE_ID, movie.getMovieId());
        return values;
    }
}
