//package com.example.andrewli.inputinformation;
package teammemes.tritonbudget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by andrewli on 2/8/17.
 */

public class Database extends SQLiteOpenHelper {
    public static final String DataBaseName = "UserInfo";
    public static final String Table_Name = "User_Table";
    public static final String COL1 = "ID";
    public static final String COL2 = "NAME";
    public static final String COL3 = "BUDGET";

    public Database(Context context) {
        super(context, DataBaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ Table_Name +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT, BUDGET INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        onCreate(db);
    }

    public boolean insertData(String name, String money){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
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
        Cursor res = db.rawQuery("select * from "+Table_Name,null);
        return res;
    }
}
