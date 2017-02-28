package teammemes.tritonbudget;

/**
 * Created by andrewli on 2/26/17.
 * Interface used to store constants
 *
 */

public interface MenuDB {
    // meal db
    public static final String Table_Menu = "Menu"; // prepopulate data
    public static final String IDCOL = "ID";
    public static final String NAMECOL = "NAME";
    public static final String LOCATIONCOL = "LOCATION";
    public static final String TYPECOL = "TYPE";
    public static final String CATEGORYCOL = "CATEGORY";
    public static final String VEGANCOL = "VEGAN";
    public static final String VEGETARIANCOL = "VEGETARIAN";
    //public static final String DAYCOL = "DAY"; // TODO: add days to table
    public static final String COSTCOL = "COST";
    static final String CREATE_DB_TABLE =  "CREATE TABLE " + Table_Menu  + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            " NAME TEXT, LOCATION TEXT, TYPE TEXT, CATEGORY TEXT, VEGETARIAN INTEGER DEFAULT 0," +
            " VEGAN INTEGER DEFAULT 0, COST DOUBLE)";
}
