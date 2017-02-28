package teammemes.tritonbudget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by andrewli on 2/25/17.
 */

public class MenuDataSource {

    // Database fields
    private SQLiteDatabase database;
    private Database dbHelper;
    private String[] allColumns = {MenuDB.IDCOL, MenuDB.NAMECOL,
            MenuDB.LOCATIONCOL, MenuDB.TYPECOL, MenuDB.CATEGORYCOL,
            MenuDB.VEGETARIANCOL, MenuDB.VEGANCOL, MenuDB.COSTCOL};

    public MenuDataSource(Context context) {
        dbHelper = new Database(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Menu createMenu(Menu menu) {
        ContentValues values = new ContentValues();
        values.put(MenuDB.NAMECOL, menu.getName());
        values.put(MenuDB.LOCATIONCOL, menu.getLocation());
        values.put(MenuDB.CATEGORYCOL, menu.getCategory());
        values.put(MenuDB.TYPECOL, menu.getType());
        values.put(MenuDB.VEGETARIANCOL, menu.isVegeterian());
        values.put(MenuDB.VEGANCOL, menu.isVegan());
        values.put(MenuDB.COSTCOL, menu.getCost());
        long insertId = database.insert(MenuDB.Table_Menu, null,
                values);
        Cursor cursor = database.query(MenuDB.Table_Menu,
                allColumns, MenuDB.IDCOL + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Menu newComment = cursorToMenu(cursor);
        cursor.close();
        return newComment;
    }

//        public void deleteMenu(Menu menu) {
//            long id = menu.getId();
//            System.out.println("Menu deleted with id: " + id);
//            database.delete(Database.Table_Menu, Database.IDCOL
//                    + " = " + id, null);
//        }

    public List<Menu> getAllMenus() {
        List<Menu> menus = new ArrayList<Menu>();
        Cursor cursor = database.query(MenuDB.Table_Menu,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Menu menu = cursorToMenu(cursor);
            menus.add(menu);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return menus;
    }

    public List<Menu> getMenusByLocation(String location) {
        List<Menu> menus = new ArrayList<Menu>();
        Cursor cursor = database.query(MenuDB.Table_Menu,
                allColumns, MenuDB.LOCATIONCOL + "= '" + location + "'", null,
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Menu menu = cursorToMenu(cursor);
            menus.add(menu);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return menus;
    }

    public List<Menu> getMenusByType(String type) {
        List<Menu> menus = new ArrayList<Menu>();
        Cursor cursor = database.query(MenuDB.Table_Menu,
                allColumns, MenuDB.TYPECOL + "= '" + type + "'", null,
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Menu menu = cursorToMenu(cursor);
            menus.add(menu);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return menus;
    }

    public List<Menu> getMenusByVegetarian() {
        List<Menu> menus = new ArrayList<Menu>();
        Cursor cursor = database.query(MenuDB.Table_Menu,
                allColumns, MenuDB.VEGETARIANCOL + "= 1", null,
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Menu menu = cursorToMenu(cursor);
            menus.add(menu);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return menus;
    }

    public List<Menu> getMenusByVegan() {
        List<Menu> menus = new ArrayList<Menu>();
        Cursor cursor = database.query(MenuDB.Table_Menu,
                allColumns, MenuDB.VEGANCOL + "= 1", null,
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Menu menu = cursorToMenu(cursor);
            menus.add(menu);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return menus;
    }

    private Menu cursorToMenu(Cursor cursor) {
        Menu menu = new Menu();
        try {
            menu.setId(cursor.getInt(cursor.getColumnIndex(MenuDB.IDCOL)));
            menu.setName(cursor.getString(cursor.getColumnIndex(MenuDB.NAMECOL)));
            menu.setLocation(cursor.getString(cursor.getColumnIndex(MenuDB.LOCATIONCOL)));
            menu.setType(cursor.getString(cursor.getColumnIndex(MenuDB.TYPECOL)));
            menu.setCategory(cursor.getString(cursor.getColumnIndex(MenuDB.CATEGORYCOL)));
            boolean vegetarian = (cursor.getInt(cursor.getColumnIndex(MenuDB.VEGETARIANCOL)) == 1);
            menu.setVegeterian(vegetarian);
            boolean vegan = (cursor.getInt(cursor.getColumnIndex(MenuDB.VEGANCOL)) == 1);
            menu.setVegan(vegan);
            menu.setCost(cursor.getDouble(cursor.getColumnIndex(MenuDB.COSTCOL)));
        } catch (Exception e) {

        }
        return menu;
    }
}