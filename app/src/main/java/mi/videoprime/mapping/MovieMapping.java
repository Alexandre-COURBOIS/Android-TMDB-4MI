package mi.videoprime.mapping;

public interface MovieMapping extends BaseMapping {

    public static final String TABLE_NAME = "movies";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_POSTERPATH = "posterPath";
    public static final String COLUMN_BACKDROPPATH = "backdropPath";
    public static final String COLUMN_VOTEAVERAGE = "voteAverage";
    public static final String COLUMN_RELEASEDATE = "releaseDate";
    public static final String COLUMN_MOVIE_ID = "movieid";
    public static final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE "
            + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 0, "
            + COLUMN_TITLE + " TEXT UNIQUE DEFAULT '', "
            + COLUMN_OVERVIEW + " TEXT DEFAULT '', "
            + COLUMN_POSTERPATH + " TEXT DEFAULT '', "
            + COLUMN_BACKDROPPATH + " TEXT DEFAULT '', "
            + COLUMN_VOTEAVERAGE + " INTEGER DEFAULT 0 , "
            + COLUMN_RELEASEDATE + " TEXT DEFAULT ''," +
            COLUMN_MOVIE_ID + " LONG DEFAULT 0);";
}
