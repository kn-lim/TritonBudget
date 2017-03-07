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

public class TransactionDataSource {
    // Database fields
    private SQLiteDatabase database;
    private Database dbHelper;

    public TransactionDataSource(Context context) {
        dbHelper = new Database(context);
    }
    public Transaction createTransaction(Transaction trans) {
        ContentValues values = new ContentValues();
        values.put(TransactionDB.MENUID, trans.getMenuId());
        values.put(TransactionDB.QUANTITY, trans.getQuantity());
        SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        values.put(TransactionDB.TDATE, dformat.format(trans.gettDate()));
        values.put(MenuDB.COSTCOL, trans.getCost());
        long insertId = database.insert(MenuDB.Table_Menu, null,
                values);
        Cursor cursor = database.query(MenuDB.Table_Menu,
                TransactionDB.allColumns, MenuDB.IDCOL + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Transaction newComment = cursorToTransaction(cursor);
        cursor.close();
        return newComment;
    }

    public List<Transaction> getAllTransaction() {
        List<Transaction> transactions = new ArrayList<Transaction>();
        Cursor cursor = database.query(TransactionDB.Table_Transaction, TransactionDB.allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Transaction transaction = cursorToTransaction(cursor);
            transactions.add(transaction);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return transactions;
    }

    public double[] getTransactionByWeek() {
        double[] cost = new double[7];
        List<TranHistory> histories= this.getAllTransaction();
        int currDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        for(int i=0;i<histories.size();i++)
        {
            Date day = histories.get(i).getTdate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(day);
            int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
            if(0<currDay-dayOfYear && currDay-dayOfYear<8)
            {
                cost[currDay-dayOfYear-1]+=histories.get(i).getCost();
            }
        }
        return cost;
    }
    public double[] getTransactionByMonth() {
        double[] cost = new double[4];
        List<TranHistory> histories= this.getAllTransaction();
        int currDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        for(int i=0;i<histories.size();i++)
        {
            Date day = histories.get(i).getTdate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(day);
            int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
            if(0<currDay-dayOfYear && currDay-dayOfYear<8)
            {
                cost[0]+=histories.get(i).getCost();
            }
            if(7<currDay-dayOfYear && currDay-dayOfYear<15)
            {
                cost[1]+=histories.get(i).getCost();
            }
            if(14<currDay-dayOfYear && currDay-dayOfYear<22)
            {
                cost[2]+=histories.get(i).getCost();
            }
            if(21<currDay-dayOfYear && currDay-dayOfYear<29)
            {
                cost[3]+=histories.get(i).getCost();
            }
        }
        return cost;
    }
    
    private Transaction cursorToTransaction(Cursor cursor) {
        Transaction transaction = new Transaction();
        try {
            transaction.setId(cursor.getInt(cursor.getColumnIndex(TransactionDB.ID)));
            transaction.setMenuId(cursor.getInt(cursor.getColumnIndex(TransactionDB.MENUID)));
            transaction.setQuantity(cursor.getInt(cursor.getColumnIndex(TransactionDB.QUANTITY)));
            SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            transaction.settDate(dformat.parse(cursor.getString(cursor.getColumnIndex(TransactionDB.TDATE))));
            transaction.setCost(cursor.getDouble(cursor.getColumnIndex(MenuDB.COSTCOL)));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return transaction;
    }
}
