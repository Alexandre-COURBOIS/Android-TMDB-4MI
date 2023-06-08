package mi.videoprime.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mi.videoprime.model.User;

public class SQLiteUserDao extends BaseDaoSqlite implements UserDao {
    public SQLiteUserDao(Context context) {
        super(context);
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String[] cols = {User.ID, User.COLUMN_USERNAME, User.COLUMN_EMAIL, User.COLUMN_PASSWORD};
        Cursor cursor = getDb().query(User.TABLE_NAME, cols, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            users.add(new User((int)cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            cursor.moveToNext();
        }
        cursor.close();
        getDb().close();
        return users;
    }

    @Override
    public User get(int id) {
        User userToGet = null;
        String[] cols = {User.ID, User.COLUMN_USERNAME, User.COLUMN_EMAIL, User.COLUMN_PASSWORD};
        String[] params = { ""+id };
        Cursor cursor = getDb().query(User.TABLE_NAME, cols, "id=?", params, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {  // meilleur qu'un try catch
            userToGet = new User((int)cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            cursor.moveToNext();
        }
        cursor.close();
        getDb().close();
        return userToGet;
    }

    @Override
    public User getUserByEmailOrUsername(String value) {
        User userToGet = null;
        String[] cols = {User.ID, User.COLUMN_USERNAME, User.COLUMN_EMAIL, User.COLUMN_PASSWORD};
        String[] params = { value, value };
        Cursor cursor = getDb().query(User.TABLE_NAME, cols, User.COLUMN_USERNAME + "=? OR " + User.COLUMN_EMAIL + "=?", params, null, null, null);

        if(cursor.moveToFirst()) {
            userToGet = new User((int)cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }

        cursor.close();
        getDb().close();
        return userToGet;
    }

    @Override
    public void update(User user) {
        ContentValues values = mapFromUser(user);
        String[] params = { ""+user.getId() };
        getDb().update(User.TABLE_NAME, values, User.ID + " = ?", params);
        getDb().close();
    }

    @Override
    public int add(User user) {
        try
        {
            ContentValues values = mapFromUser(user);
            int id = (int) getDb().insert(User.TABLE_NAME, null, values);
            getDb().close();
            return id;
        }
        catch (SQLiteConstraintException e)
        {
            Log.e("Database", "error", e);
            return -1;
        }
    }

    private ContentValues mapFromUser(User user){
        ContentValues values = new ContentValues();
        values.put(User.COLUMN_USERNAME, user.getUsername());
        values.put(User.COLUMN_EMAIL, user.getEmail());
        values.put(User.COLUMN_PASSWORD, user.getPassword());
        return values;
    }
}
