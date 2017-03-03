package teammemes.tritonbudget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by andrewli on 2/8/17.
 * edited 3/3/17
 */

public class Database extends SQLiteOpenHelper {

    public static final String DataBaseName = "Triton_Budget";
    // nutrition
    public static final String Table_Nutrition = "Nutrition";// prepopulate data
    //
    public static final String Table_Menu_Status = "Status";
    private Context localContext;


    public Database(Context context) {
        super(context, DataBaseName, null,1);
        localContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(UserDB.CREATE_USER_TABLE);
        // menu db
        db.execSQL(MenuDB.CREATE_DB_TABLE);
        // transaction table
        db.execSQL(TransactionDB.CREATE_TRANSACTION_TABLE);
        InputStream inputStream = localContext.getResources().openRawResource(R.raw.menu);
        populateMenu(inputStream, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserDB.Table_User);
        db.execSQL("DROP TABLE IF EXISTS " + MenuDB.Table_Menu);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserDB.Table_User);
        db.execSQL("DROP TABLE IF EXISTS " + MenuDB.Table_Menu);
        onCreate(db);
    }

    /**
     * inserts user's name and money
     * @param name
     * @param money
     * @return
     */
    public boolean insertData(String name, String money){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDB.COL1, 1);
        contentValues.put(UserDB.COL2, name);
        contentValues.put(UserDB.COL3, money);

        long result = db.insert(UserDB.Table_User,null,contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select ID, Name, Budget from "+UserDB.Table_User,null);
        return res;
    }

    /**
     * update user
     * @param id
     * @param name
     * @param money
     * @return
     */
    public boolean updateData(String id,String name,String money) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDB.COL1,id);
        contentValues.put(UserDB.COL2,name);
        contentValues.put(UserDB.COL3,money);
        long result = db.update(UserDB.Table_User, contentValues, "ID = 1",new String[] { id });
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * delete your user
     * @param id
     * @return
     */
    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(UserDB.Table_User, "ID = ?",new String[] {id});
    }

    /**
     * populated the menu from a csv file
     * @param inputStream
     * @param db
     */
    public void populateMenu(InputStream inputStream, SQLiteDatabase db) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                //Location,Type,Category,Name,Cost,Palate
                String[] fields = csvLine.split(",");
                ContentValues contentValues = new ContentValues();
                contentValues.put(MenuDB.LOCATIONCOL, fields[0]);
                contentValues.put(MenuDB.TYPECOL, fields[1]);
                contentValues.put(MenuDB.CATEGORYCOL, fields[2]);
                contentValues.put(MenuDB.NAMECOL, fields[3]);
                contentValues.put(MenuDB.COSTCOL, Double.parseDouble(fields[4]));
                int vegetarian =  "true".equals(fields[5])?1:0;
                contentValues.put(MenuDB.VEGETARIANCOL, vegetarian);
                int vegan =  "true".equals(fields[6])?1:0;
                contentValues.put(MenuDB.VEGANCOL, vegan);
                int gluten =  "true".equals(fields[7])?1:0;
                contentValues.put(MenuDB.GLUTENCOL, gluten);

                db.insert(MenuDB.Table_Menu,null,contentValues);
            }

        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
    }
}
