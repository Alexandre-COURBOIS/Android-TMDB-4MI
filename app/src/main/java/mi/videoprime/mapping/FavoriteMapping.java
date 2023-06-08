package mi.videoprime.mapping;

public interface FavoriteMapping extends BaseMapping {

    public static final String TABLE_NAME = "favorites";
    public static final String COLUMN_MOVIEID = "movieId";
    public static final String COLUMN_MOVIETITLE = "movieTitle";
    public static final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE "
            + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_MOVIEID + " INTEGER NOT NULL UNIQUE, "
            + COLUMN_MOVIETITLE + " TEXT NOT NULL UNIQUE);";
}
