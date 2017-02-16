//package com.example.danny.hdhmenu;
package teammemes.tritonbudget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class MainActivity extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.info);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Burger Lounge");
        listDataHeader.add("Revelle Cuisine");
        listDataHeader.add("Market 64");
        listDataHeader.add("Vertically Crafted Deli");
        listDataHeader.add("Wok");
        // Adding child data
        List<String> BL = new ArrayList<String>();
        BL.add("Chipotle Black Bean Burger  ($5.25)");
        BL.add("Gardein Burger  ($5.25)");
        BL.add("Hamburger  ($5.25)");
        BL.add("Herbed Chicken Sandwich  ($5.25)");
        BL.add("Turkey Burger  ($5.25)");
        BL.add("French Fries  ($1.75)");
        BL.add("Sweet Potato Fries  ($2.75)");
        BL.add("Buffalo Chicken Oozer  ($6.64)");

        List<String> RC = new ArrayList<String>();
        RC.add("Smoked Tri Tip Churrasco  ($6.75)");
        RC.add("Tandoori Chicken  ($4.75)");
        RC.add("Black Rice Napa Cabbage Salad  ($2.00)");
        RC.add("Butternut Asparagus Sautee  ($2.00)");
        RC.add("Eggplant Caponatta  ($2.00)");
        RC.add("House Side Salad  ($2.00)");
        RC.add("Roasted Beets  ($2.00)");
        RC.add("Roasted Red Potatoes  ($2.00)");

        List<String> MT = new ArrayList<String>();
        MT.add("Potato w/ Bacon Soup  ($3.50)");
        MT.add("Vegetarian Chili  ($3.50)");
        MT.add("Buffalo Blue Chicken Salad  ($8.50)");

        List<String> VCD = new ArrayList<String>();
        VCD.add("Fresh Deli Station  ($6.75)");

        List<String> WK = new ArrayList<String>();
        WK.add("Pork Potstickers");
        WK.add("Crunchy Orange Chicken  ($6.95)");
        WK.add("Spicy Dragon Shrimp  ($6.95)");
        listDataChild.put(listDataHeader.get(0), BL); // Header, Child data
        listDataChild.put(listDataHeader.get(1), RC);
        listDataChild.put(listDataHeader.get(2), MT);
        listDataChild.put(listDataHeader.get(3), VCD);
        listDataChild.put(listDataHeader.get(4), WK);

    }
}