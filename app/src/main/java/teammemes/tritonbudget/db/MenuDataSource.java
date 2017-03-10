package teammemes.tritonbudget.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import teammemes.tritonbudget.Menu;


/**
 * Created by andrewli on 2/25/17.
 */


public class MenuDataSource extends BaseDataSource {

    public MenuDataSource(Context context) {
        super(context);
    }

    public Menu createMenu(Menu menu) {
        ContentValues values = new ContentValues();
        values.put(MenuDB.NAMECOL, menu.getName());
        values.put(MenuDB.LOCATIONCOL, menu.getLocation());
        values.put(MenuDB.CATEGORYCOL, menu.getCategory());
        values.put(MenuDB.TYPECOL, menu.getType());
        values.put(MenuDB.VEGETARIANCOL, menu.isVegeterian());
        values.put(MenuDB.VEGANCOL, menu.isVegan());
        values.put(MenuDB.GLUTENCOL, menu.isGluten());
        values.put(MenuDB.COSTCOL, menu.getCost());
        long insertId = database.insert(MenuDB.Table_Menu, null, values);
        Cursor cursor = database.query(MenuDB.Table_Menu, MenuDB.allColumns, MenuDB.IDCOL + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Menu newComment = cursorToMenu(cursor);
        cursor.close();
        return newComment;
    }

    // public void deleteMenu(Menu menu) {
    // long id = menu.getId();
    // System.out.println("Menu deleted with id: " + id);
    // database.delete(Database.Table_Menu, Database.IDCOL
    // + " = " + id, null);
    // }

    /**
     * get all menus in the whole database
     * @return
     */
    public List<Menu> getAllMenus() {
        List<Menu> menus = new ArrayList<Menu>();
        Cursor cursor = database.query(MenuDB.Table_Menu, MenuDB.allColumns, null, null, null, null, null);
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
        Cursor cursor = database.query(MenuDB.Table_Menu, MenuDB.allColumns,
                MenuDB.LOCATIONCOL + "= '" + location + "'", null, null, null, null);
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
    public List<String> getCategoriesByLocation(String location) {
        List<String> menus = new ArrayList<String>();
        Cursor cursor = database.query(MenuDB.Table_Menu, MenuDB.allColumns,
                MenuDB.LOCATIONCOL + "= '" + location + "'", null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Menu menu = cursorToMenu(cursor);
            if((menus.size() == 0) || (!menus.get(menus.size()-1).equals(menu.getCategory()))) {
                menus.add(menu.getCategory());
            }
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return menus;
    }


    public List<Menu> getMenusByCategory(String category) {
        List<Menu> menus = new ArrayList<Menu>();
        Cursor cursor = database.query(MenuDB.Table_Menu, MenuDB.allColumns,
                MenuDB.CATEGORYCOL + "= '" + category + "'", null, null, null, null);
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

    /**
     * return all food by location and category
     *
     * @param location
     * @param category
     * @return return list of food by location and category
     */
    public List<Menu> getMenusByLocationAndCategory(String location, String category) {
        List<Menu> menus = new ArrayList<Menu>();
        // sort the current database by location type and category
        Cursor cursor = database.query(MenuDB.Table_Menu, MenuDB.allColumns,
                MenuDB.LOCATIONCOL + "= ? and " + MenuDB.CATEGORYCOL + " = ? ", new String[] { location, category },
                null, null, MenuDB.NAMECOL + " DESC");

        cursor.moveToFirst();
        // add to the menu list
        while (!cursor.isAfterLast()) {
            Menu menu = cursorToMenu(cursor);
            menus.add(menu);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        // returns list of menus
        return menus;
    }

    /**
     * Method to return list of menus sorted by specific location and time of
     * day
     *
     * @param location
     *            String variable of "Pines"
     * @param type
     *            Breakfast, Lunch, Dinner, all
     * @return list of menus
     */
    public List<Menu> getMenusByLocationAndType(String location, String type) {
        List<Menu> menus = new ArrayList<Menu>();
        // sort the current database by location type and category
        Cursor cursor = database.query(MenuDB.Table_Menu, MenuDB.allColumns,
                MenuDB.LOCATIONCOL + "= ? and " + MenuDB.TYPECOL + " = ? ", new String[] { location, type }, null, null,
                MenuDB.NAMECOL + " DESC");

        // move to front of cursor
        cursor.moveToFirst();
        // add to the menu list
        while (!cursor.isAfterLast()) {
            Menu menu = cursorToMenu(cursor);
            menus.add(menu);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        // returns list of menus
        return menus;
    }

    /**
     * Method to return list of menus sorted by specific location and time of
     * day and category
     *
     * @param location
     *            String variable of "Pines"
     * @param type
     *            Breakfast, Lunch, Dinner, all
     * @param category
     *            whole variety
     * @return list of menus
     */
    public List<Menu> getMenusByLocationAndTypeAndCategory(String location, String type, String category) {
        List<Menu> menus = new ArrayList<Menu>();

        // sort the current database by location type and category
        Cursor cursor = database.query(MenuDB.Table_Menu, MenuDB.allColumns,
                MenuDB.LOCATIONCOL + "= ? and " + MenuDB.TYPECOL + " = ? and " + MenuDB.CATEGORYCOL + " = ?",
                new String[] { location, type, category }, null, null, MenuDB.NAMECOL + " DESC");

        // move to front of cursor
        cursor.moveToFirst();
        // add to the menu list
        while (!cursor.isAfterLast()) {
            Menu menu = cursorToMenu(cursor);
            menus.add(menu);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        // returns list of menus
        return menus;
    }

    /**
     * return all vegetarian items
     *
     * @return list of vegatarian items
     */
    public List<Menu> getMenusByVegetarian() {
        List<Menu> menus = new ArrayList<Menu>();

        // sort the current database by Vegetarian food only
        Cursor cursor = database.query(MenuDB.Table_Menu, MenuDB.allColumns, MenuDB.VEGETARIANCOL + "= 1", null, null,
                null, null);

        // move to front of cursor
        cursor.moveToFirst();
        // add to the menu list
        while (!cursor.isAfterLast()) {
            Menu menu = cursorToMenu(cursor);
            menus.add(menu);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        // returns list of menus
        return menus;
    }

    /**
     * return all vegan items
     *
     * @return list of all vegan items
     */
    public List<Menu> getMenusByVegan() {
        List<Menu> menus = new ArrayList<Menu>();
        // sort the current database by Vegan food only
        Cursor cursor = database.query(MenuDB.Table_Menu, MenuDB.allColumns, MenuDB.VEGANCOL + "= 1", null, null, null,
                null);

        // move to front of cursor
        cursor.moveToFirst();
        // add to the menu list
        while (!cursor.isAfterLast()) {
            Menu menu = cursorToMenu(cursor);
            menus.add(menu);
            cursor.moveToNext();
        }

        // make sure to close the cursor
        cursor.close();
        return menus;
    }

    /**
     * return all vegan items
     *
     * @return list of all vegan items
     */
    public List<Menu> getMenusByGluten() {
        List<Menu> menus = new ArrayList<Menu>();
        // sort the current database by gluten food only
        Cursor cursor = database.query(MenuDB.Table_Menu, MenuDB.allColumns, MenuDB.GLUTENCOL + "= 1", null, null, null,
                null);

        // move to front of cursor
        cursor.moveToFirst();
        // add to the menu list
        while (!cursor.isAfterLast()) {
            Menu menu = cursorToMenu(cursor);
            menus.add(menu);
            cursor.moveToNext();
        }

        // make sure to close the cursor
        cursor.close();
        return menus;
    }


    /**
     * Menu by Id
     */

    public Menu getMenuById(int id){
        Cursor cursor = database.query(MenuDB.Table_Menu, MenuDB.allColumns, MenuDB.IDCOL + "= ?", new String[]{""+id}, null, null,
                null);
        cursor.moveToFirst();
        if(!cursor.isAfterLast()) {
            Menu menu = cursorToMenu(cursor);
            cursor.close();
            return menu;
        }
        else
            return null;


    }
    /**
     * Converts cursor object into a menu object
     *
     * @param cursor
     * @return menu object
     */
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
            boolean gluten = (cursor.getInt(cursor.getColumnIndex(MenuDB.GLUTENCOL)) == 1);
            menu.setGluten(gluten);
            menu.setCost(cursor.getDouble(cursor.getColumnIndex(MenuDB.COSTCOL)));
        } catch (Exception e) {
            Log.e("curorToMenu",e.getMessage(),e);
        }
        return menu;
    }
}