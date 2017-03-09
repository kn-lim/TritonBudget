package teammemes.tritonbudget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import teammemes.tritonbudget.db.MenuDataSource;

public class MenuCafeVentanas_Main extends AppCompatActivity {
    public MenuDataSource DS;
    String dhname = "Cafe Ventanas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_display_main);
        // Init top level data

        DS = new MenuDataSource(this);

        //

        List<String> listDataHeader = new ArrayList<>();
        String[] mItemHeaders = new String[16];
        //String[] mItemHeaders = getResources().getStringArray(R.array.items_array_expandable_level_one);

        mItemHeaders[1] = "Breakfast Special";
        mItemHeaders[2] = "Daily Hot Breakfast";
        mItemHeaders[3] = "Cold Breakfast";
        mItemHeaders[4] = "Value4u";
        mItemHeaders[5] = "Windows of the World";
        mItemHeaders[6] = "Windows Sides";
        mItemHeaders[7] = "City Dish Entrees";
        mItemHeaders[8] = "City Dish Sides";
        mItemHeaders[9] = "Deli";
        mItemHeaders[10] = "Grill";
        mItemHeaders[11] = "Grill Sides";
        mItemHeaders[12] = "Pizza Oven";
        mItemHeaders[13] = "Soup";
        mItemHeaders[14] = "Pasta";
        mItemHeaders[15] = "Pasta Sides";
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