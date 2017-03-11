package teammemes.tritonbudget.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import teammemes.tritonbudget.R;


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
        super(context, DataBaseName, null,9); // 10 is just version of database for testing
        localContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(UserDB.CREATE_USER_TABLE);
        // menu db
        db.execSQL(MenuDB.CREATE_DB_TABLE);
        // transaction table
        db.execSQL(HistoryDB.CREATE_TRANSACTION_TABLE);
        // so can add unit test case run from android env
        if(localContext != null) {
            InputStream inputStream = localContext.getResources().openRawResource(R.raw.menu);
            populateMenu(inputStream, db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserDB.Table_User);
        db.execSQL("DROP TABLE IF EXISTS " + MenuDB.Table_Menu);
        db.execSQL("DROP TABLE IF EXISTS " + HistoryDB.Table_Transaction);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserDB.Table_User);
        db.execSQL("DROP TABLE IF EXISTS " + MenuDB.Table_Menu);
        db.execSQL("DROP TABLE IF EXISTS " + HistoryDB.Table_Transaction);
        onCreate(db);
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
                try {
                    String[] fields = csvLine.split(",");
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MenuDB.LOCATIONCOL, fields[0]);
                    contentValues.put(MenuDB.TYPECOL, fields[1]);
                    contentValues.put(MenuDB.CATEGORYCOL, fields[2]);
                    contentValues.put(MenuDB.NAMECOL, fields[3]);
                    contentValues.put(MenuDB.COSTCOL, Double.parseDouble(fields[4]));
                    int vegetarian = "TRUE".equals(fields[5]) ? 1 : 0;
                    contentValues.put(MenuDB.VEGETARIANCOL, vegetarian);
                    int vegan = "TRUE".equals(fields[6]) ? 1 : 0;
                    contentValues.put(MenuDB.VEGANCOL, vegan);
                    int gluten = "TRUE".equals(fields[7]) ? 1 : 0;
                    contentValues.put(MenuDB.GLUTENCOL, gluten);

                    db.insert(MenuDB.Table_Menu, null, contentValues);
                }catch(Exception e){
                    Log.e("Populate Menu",e.getMessage(),e);
                }

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