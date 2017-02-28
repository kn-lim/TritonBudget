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
 */

public class Database extends SQLiteOpenHelper {

    // user table
    public static final String DataBaseName = "Triton_Budget";
    public static final String Table_Name = "User";
    public static final String COL1 = "ID";
    public static final String COL2 = "NAME";
    public static final String COL3 = "BUDGET";


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

        db.execSQL("create table "+ Table_Name +" (ID INTEGER PRIMARY KEY,NAME TEXT, BUDGET INTEGER)");
        // menu db
        db.execSQL(MenuDB.CREATE_DB_TABLE);
        // transaction table
        db.execSQL(TransactionDB.CREATE_TRANSACTION_TABLE);
        InputStream inputStream = localContext.getResources().openRawResource(R.raw.menu);
        populateMenu(inputStream, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        db.execSQL("DROP TABLE IF EXISTS " + MenuDB.Table_Menu);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        db.execSQL("DROP TABLE IF EXISTS " + MenuDB.Table_Menu);
        onCreate(db);
    }

    public boolean insertData(String name, String money){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, 1);
        contentValues.put(COL2, name);
        contentValues.put(COL3, money);

        long result = db.insert(Table_Name,null,contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select ID,Category, Type, Name, Vegetarian, Vegan Cost from "+MenuDB.Table_Menu,null);
        return res;
    }
    public boolean updateData(String id,String name,String money) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1,id);
        contentValues.put(COL2,name);
        contentValues.put(COL3, money);
        long result = db.update(Table_Name, contentValues, "ID = ?",new String[] { id });
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Table_Name, "ID = ?",new String[] {id});
    }
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

