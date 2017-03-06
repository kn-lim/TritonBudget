package teammemes.tritonbudget.db;

/**
 * Created by andrewli on 2/26/17.
 */

public interface HistoryDB {

    static String ID = "ID"; // transaction ID
    static String MENUID = "MENUID";
    static String QUANTITY = "QUANTITY";
    static String TDATE = "TDATE";
    static String COST = "COST";


    static final String Table_Transaction = "TranHistory";
    static final String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + Table_Transaction + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            " MENUID INTEGER, QUANTITY INTEGER, TDATE TEXT, COST DOUBLE)";
    static String[] allColumns = {ID, MENUID, QUANTITY, TDATE, COST};


}
