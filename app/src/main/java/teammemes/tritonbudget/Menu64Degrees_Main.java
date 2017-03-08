package teammemes.tritonbudget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import teammemes.tritonbudget.db.MenuDataSource;

public class Menu64Degrees_Main extends AppCompatActivity {
    public MenuDataSource DS;
    String dhname = "64 Degrees";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_display_main);
        // Init top level data

        DS = new MenuDataSource(this);

        //

        List<String> listDataHeader = new ArrayList<>();
        String[] mItemHeaders = new String[6];
        //String[] mItemHeaders = getResources().getStringArray(R.array.items_array_expandable_level_one);

        mItemHeaders[1] = "Burger Lounge";
        mItemHeaders[2] = "Revelle Cuisine";
        mItemHeaders[3] = "Market 64";
        mItemHeaders[4] = "Vertically Crafted Deli";
        mItemHeaders[5] = "Wok";

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