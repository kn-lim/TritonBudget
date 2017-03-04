package teammemes.tritonbudget;

/**
 * Created by andrewli on 3/2/17.
 * Interface used to store user strings
 */

public interface UserDB {
    // user table
    public static final String Table_User = "User";
    public static final String IDCOL = "ID";
    public static final String NAMECOL = "NAME";
    public static final String BALANCECOL = "BUDGET";
    public static final String CREATE_USER_TABLE = "create table "+ Table_User +
            " (ID INTEGER PRIMARY KEY,NAME TEXT, BALANCE INTEGER)";
    public static String[] allColumns = { IDCOL, NAMECOL,
            BALANCECOL};
}
