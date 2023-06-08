package mi.videoprime.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import mi.videoprime.helper.DBHelper;

public class BaseDaoSqlite {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public BaseDaoSqlite(Context context){
        dbHelper = new DBHelper(context);
    }

    protected SQLiteDatabase getDb(){
        if(db== null || !db.isOpen()){
            db = dbHelper.getWritableDatabase();
        }
        return db;
    }

    public DBHelper getDbHelper() {
        return dbHelper;
    }
}