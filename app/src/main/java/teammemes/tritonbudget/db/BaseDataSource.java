package teammemes.tritonbudget.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Andrew Lion 3/4/2017.
 */

public class BaseDataSource implements AutoCloseable{
    // Database fields
    protected SQLiteDatabase database;
    protected  Database dbHelper;

    public BaseDataSource(Context context) {
        dbHelper = new Database(context);
        open();
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
}
