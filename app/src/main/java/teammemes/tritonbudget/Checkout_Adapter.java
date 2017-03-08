package teammemes.tritonbudget;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.content.Context;

import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Zi Y Liu on 3/6/17.
 * Custom ArrayAdapter for members of checkout
 */

public class Checkout_Adapter extends ArrayAdapter<Checkout_Item>{
    public Checkout_Adapter(Context context, ArrayList<Checkout_Item> Item_Object) {
        super(context, 0, Item_Object);
    }

    public View getView(int position, View conView, ViewGroup ItemsGrouping) {

        //retrieve the data for food_item at certain position
        Checkout_Item item = getItem(position);

        if (conView == null) {
            conView = LayoutInflater.from(getContext()).inflate(R.layout.content_checkout, ItemsGrouping, false);
        }

        //go into xml files and change id names

        TextView itemName = (TextView) conView.findViewById(R.id.COItemName);
        TextView itemQuantity = (TextView) conView.findViewById(R.id.COItemQuantity);
        TextView itemPrice = (TextView) conView.findViewById(R.id.COItemPrice);
        TextView multItemPrice = (TextView) conView.findViewById(R.id.COItemTPrice);


        itemName.setText(item.food_item);
        itemQuantity.setText(item.quantity);
        itemPrice.setText(String.valueOf(item.price));
        multItemPrice.setText(String.valueOf(item.total_price));

        return conView;
    }
}
