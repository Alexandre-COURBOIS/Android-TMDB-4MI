package mi.videoprime.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import javax.annotation.Nullable;

import mi.videoprime.model.Favorite;
import mi.videoprime.model.Movie;
import mi.videoprime.model.User;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userDatabase.db";
    private static final int DATABASE_VERSION = 2;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DB", db.toString());
        db.execSQL(User.SQL_CREATE_USER_TABLE);
        db.execSQL(Favorite.SQL_CREATE_FAVORITE_TABLE);
        db.execSQL(Movie.SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Favorite.TABLE_NAME);
        db.execSQL(User.SQL_CREATE_USER_TABLE);
        db.execSQL(Favorite.SQL_CREATE_FAVORITE_TABLE);
        db.execSQL(Movie.SQL_CREATE_MOVIE_TABLE);
    }
}
