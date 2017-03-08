package teammemes.tritonbudget;

/**
 * Created by Zi Y Liu on 3/6/17.
 * Custom ArrayList for checkout items
 */

import java.util.ArrayList;

public class Checkout_Item {
    public String food_item;
    public int quantity;
    public float price;

    public float total_price;

    public Checkout_Item(String food_item, int quantity, float price) {
        this.food_item = food_item;
        this.quantity = quantity;
        this.price = price;

        this.total_price = quantity*price;
    }

    public static ArrayList<Checkout_Item> getFoodItems() {
        ArrayList<Checkout_Item> ItemList = new ArrayList<Checkout_Item>();

        //replace with actual values
        ItemList.add(new Checkout_Item("", 0, 0));

        return ItemList;
    }
}

