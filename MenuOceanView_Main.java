package teammemes.tritonbudget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import teammemes.tritonbudget.db.MenuDataSource;

/**
 * Created by Bao on 3/8/2017.
 */

public class MenuOceanView_Main extends AppCompatActivity{
    public MenuDataSource DS;
    String dhname = "Ocean View";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_display_main);
        // Init top level data

        DS = new MenuDataSource(this);

        //

        List<String> listDataHeader = new ArrayList<>();
        String[] mItemHeaders = new String[5];
        //String[] mItemHeaders = getResources().getStringArray(R.array.items_array_expandable_level_one);

        mItemHeaders[1] = "Spice Station";
        mItemHeaders[2] = "Sliceria by Scholars";
        mItemHeaders[3] = "40/40/20";
        mItemHeaders[4] = "Scholars Pizza";

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
