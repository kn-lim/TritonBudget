package teammemes.tritonbudget;

/**
 * Created by andrewli on 3/2/17.
 * Interface used to store user strings
 */

public interface UserDB {
    // user table
    public static final String Table_User = "User";
    public static final String COL1 = "ID";
    public static final String COL2 = "NAME";
    public static final String COL3 = "BUDGET";
    public static final String CREATE_USER_TABLE = "create table "+ Table_User +
            " (ID INTEGER PRIMARY KEY,NAME TEXT, BUDGET INTEGER)";

}
