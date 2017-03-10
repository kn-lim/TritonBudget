package teammemes.tritonbudget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import teammemes.tritonbudget.db.TranHistory;

/**
 * Created by Zi Y Liu on 3/6/17.
 * Custom ArrayAdapter for members of checkout
 */

public class Checkout_Adapter extends ArrayAdapter<TranHistory> {
    public Checkout_Adapter(Context context, ArrayList<TranHistory> Item_Object) {
        super(context, 0, Item_Object);
    }

    @Override
    public View getView(int position, View conView, ViewGroup ItemsGrouping) {

        //retrieve the data for food_item at certain position
        TranHistory item = getItem(position);

        if (conView == null) {
            conView = LayoutInflater.from(getContext()).inflate(R.layout.content_checkout, ItemsGrouping, false);
        }

        //go into xml files and change id names

        TextView itemName = (TextView) conView.findViewById(R.id.COItemName);
        TextView itemPrice = (TextView) conView.findViewById(R.id.COItemPrice);


        itemName.setText(item.getName());
        itemPrice.setText(String.valueOf(item.getCost()));

        return conView;
    }
}
