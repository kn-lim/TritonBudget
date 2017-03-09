package teammemes.tritonbudget;

/**
 * Created by Danny on 3/8/17.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import teammemes.tritonbudget.db.MenuDataSource;

public class MenuCanyonVista_Main extends AppCompatActivity {
    public MenuDataSource DS;
    String dhname = "Canyon Vista";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_display_main);
        // Init top level data

        DS = new MenuDataSource(this);

        //

        List<String> listDataHeader = new ArrayList<>();
        String[] mItemHeaders = new String[17];
        //String[] mItemHeaders = getResources().getStringArray(R.array.items_array_expandable_level_one);
        mItemHeaders[1] = "Value4u";
        mItemHeaders[2] = "Breakfast Special";
        mItemHeaders[3] = "Daily Hot Breakfast";
        mItemHeaders[4] = "Cold Breakfast";
        mItemHeaders[5] = "Breakfast Sides";
        mItemHeaders[6] = "Daily Cold Breakfast";
        mItemHeaders[7] = "Mediterranean";
        mItemHeaders[8] = "Canyon Noodles";
        mItemHeaders[9] = "Tecolote Grill";
        mItemHeaders[10] = "Grill Sides";
        mItemHeaders[11] = "Cliff Hanger Deli";
        mItemHeaders[12] = "Pizza";
        mItemHeaders[13] = "Salad Bar";
        mItemHeaders[14] = "Soup";
        mItemHeaders[15] = "Chopping Block";
        mItemHeaders[16] = "Chopping Block Sides";


        Collections.addAll(listDataHeader, mItemHeaders);
        final ExpandableListView mExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView_Parent);
        if (mExpandableListView != null) {

            //MenuDisplay_ParentLevelAdapter parentLevelAdapter = new MenuDisplay_ParentLevelAdapter(this, listDataHeader, mListData_SecondLevel_Map);
            MenuDisplay_ParentLevelAdapter parentLevelAdapter = new MenuDisplay_ParentLevelAdapter(this, listDataHeader, DS,dhname);
            mExpandableListView.setAdapter(parentLevelAdapter);
            // display only one expand item
//            mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//                int previousGroup = -1;
//                @Override
//                public void onGroupExpand(int groupPosition) {
//                    if (groupPosition != previousGroup)
//                        mExpandableListView.collapseGroup(previousGroup);
//                    previousGroup = groupPosition;
//                }
//            });
        }
    }
}