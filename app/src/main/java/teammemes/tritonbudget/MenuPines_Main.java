package teammemes.tritonbudget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import teammemes.tritonbudget.db.MenuDataSource;

public class MenuPines_Main extends AppCompatActivity {
    public MenuDataSource DS;
    String dhname = "Pines";

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
        mItemHeaders[2] = "Standard Breakfast";
        mItemHeaders[3] = "Value 4 U";
        mItemHeaders[4] = "Breakfast A La Carte";
        mItemHeaders[5] = "Fruit Bar";
        mItemHeaders[6] = "Parfait Bar";
        mItemHeaders[7] = "Cantina Entrees";
        mItemHeaders[8] = "Cantina Sides";
        mItemHeaders[9] = "Pizza";
        mItemHeaders[10] = "Trattoria";
        mItemHeaders[11] = "Sides";
        mItemHeaders[12] = "Chili";
        mItemHeaders[13] = "Build Your Own Salad / Fruit / Parfait";
        mItemHeaders[14] = "Stirfry";
        mItemHeaders[15] = "Sushi";
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