package teammemes.tritonbudget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrewli on 2/26/17.
 */

public class HistoryDataSource {
    // Database fields
    private SQLiteDatabase database;
    private Database dbHelper;

    public HistoryDataSource(Context context) {
        dbHelper = new Database(context);
    }
    public History createTransaction(History trans) {
        ContentValues values = new ContentValues();
        values.put(HistoryDB.MENUID, trans.getMenuId());
        values.put(HistoryDB.QUANTITY, trans.getQuantity());
        SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        values.put(HistoryDB.TDATE, dformat.format(trans.gettDate()));
        values.put(HistoryDB.COST, trans.getCost());
        long insertId = database.insert(MenuDB.Table_Menu, null,
                values);
        Cursor cursor = database.query(MenuDB.Table_Menu,
                HistoryDB.allColumns, MenuDB.IDCOL + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        History newComment = cursorToHistory(cursor);
        cursor.close();
        return newComment;
    }

    public List<History> getAllTransaction() {
        List<History> transactions = new ArrayList<History>();
        Cursor cursor = database.query(HistoryDB.Table_Transaction, HistoryDB.allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            History history = cursorToHistory(cursor);
            transactions.add(history);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return transactions;
    }

    private History cursorToHistory(Cursor cursor) {
        History transaction = new History();
        try {
            transaction.setId(cursor.getInt(cursor.getColumnIndex(HistoryDB.ID)));
            transaction.setMenuId(cursor.getInt(cursor.getColumnIndex(HistoryDB.MENUID)));
            transaction.setQuantity(cursor.getInt(cursor.getColumnIndex(HistoryDB.QUANTITY)));
            SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            transaction.settDate(dformat.parse(cursor.getString(cursor.getColumnIndex(HistoryDB.TDATE))));
            transaction.setCost(cursor.getDouble(cursor.getColumnIndex(MenuDB.COSTCOL)));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return transaction;
    }
}
