package teammemes.tritonbudget.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by Andrew Li on 3/4/2017.
 */

public class UserDataSource extends BaseDataSource {
    public UserDataSource(){
        super(null);
    }

    public UserDataSource(Context context){
        super(context);
    }

    public boolean createUser(User user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDB.IDCOL, user.getId());
        contentValues.put(UserDB.NAMECOL, user.getName());
        contentValues.put(UserDB.BALANCECOL, user.getBalance());

        long result = db.insert(UserDB.Table_User,null,contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public User getUser(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(UserDB.Table_User, UserDB.allColumns, UserDB.IDCOL +" = "+
                id,null,null,null,null);
        cursor.moveToFirst();
        User user = null;
        if(!cursor.isAfterLast()) {
           user = new User(cursor.getString(cursor.getColumnIndex(UserDB.NAMECOL)),
                   cursor.getDouble(cursor.getColumnIndex(UserDB.BALANCECOL)),id);
        }
        // make sure to close the cursor
        cursor.close();
        return user;
    }

    /**
     * Update if exists otherwise insert
     * @param user
     * @return
     */
    public boolean updateInsertUser(User user){
        User tuser = getUser(user.getId());

        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDB.IDCOL,user.getId());
        contentValues.put(UserDB.NAMECOL,user.getName());
        contentValues.put(UserDB.BALANCECOL,user.getBalance());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = 0;
        if(tuser == null){
            result = db.insert(UserDB.Table_User,null,contentValues);
        }else{
            result = db.update(UserDB.Table_User, contentValues, "ID = "+ user.getId(),null);
        }

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }


}
