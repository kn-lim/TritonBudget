package teammemes.tritonbudget.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by andrewli on 2/26/17.
 */

public class HistoryDataSource extends BaseDataSource{

    public HistoryDataSource(Context context) {
        super(context);
    }

    public TranHistory createTransaction(TranHistory trans) {
        ContentValues values = new ContentValues();
        values.put(HistoryDB.MENUID, trans.getMenuId());
        values.put(HistoryDB.QUANTITY, trans.getQuantity());
        SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        values.put(HistoryDB.TDATE, dformat.format(trans.getTdate()));
        values.put(MenuDB.COSTCOL, trans.getCost());
        long insertId = database.insert(HistoryDB.Table_Transaction, null,
                values);
        Cursor cursor = database.query(HistoryDB.Table_Transaction,
                HistoryDB.allColumns, MenuDB.IDCOL + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        TranHistory newTran = cursorToTransaction(cursor);
        cursor.close();
        return newTran;
    }

    public List<TranHistory> getAllTransaction() {
        List<TranHistory> transactions = new ArrayList<TranHistory>();
        Cursor cursor = database.query(HistoryDB.Table_Transaction, HistoryDB.allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TranHistory transaction = cursorToTransaction(cursor);
            transactions.add(transaction);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return transactions;
    }

    private TranHistory cursorToTransaction(Cursor cursor) {
        TranHistory transaction = new TranHistory();
        try {
            transaction.setId(cursor.getInt(cursor.getColumnIndex(HistoryDB.ID)));
            transaction.setMenuId(cursor.getInt(cursor.getColumnIndex(HistoryDB.MENUID)));
            transaction.setQuantity(cursor.getInt(cursor.getColumnIndex(HistoryDB.QUANTITY)));
            SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            transaction.setTdate(dformat.parse(cursor.getString(cursor.getColumnIndex(HistoryDB.TDATE))));
            transaction.setCost(cursor.getDouble(cursor.getColumnIndex(MenuDB.COSTCOL)));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return transaction;
    }
}
