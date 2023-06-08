package mi.videoprime.mapping;

public interface UserMapping extends BaseMapping {

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String SQL_CREATE_USER_TABLE = "CREATE TABLE "
            + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USERNAME + " TEXT NOT NULL UNIQUE, "
            + COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, "
            + COLUMN_PASSWORD + " TEXT NOT NULL);";
}
