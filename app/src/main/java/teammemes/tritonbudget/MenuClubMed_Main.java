package teammemes.tritonbudget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import teammemes.tritonbudget.db.MenuDataSource;

/**
 * Created by Ziying on 3/8/17.
 */

public class MenuClubMed_Main extends AppCompatActivity {
    public MenuDataSource DS;
    String dhname = "Club Med";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_display_main);
        // Init top level data

        DS = new MenuDataSource(this);

        List<String> listDataHeader = new ArrayList<>();
        String[] mItemHeaders = new String[5];

        mItemHeaders[1] = "Breakfast Specials";
        mItemHeaders[2] = "Flatbreads";
        mItemHeaders[3] = "Signature Salads";
        mItemHeaders[4] = "Turbo Salads";
        mItemHeaders[5] = "Soups";

        Collections.addAll(listDataHeader, mItemHeaders);
        final ExpandableListView mExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView_Parent);
        if(mExpandableListView!=null) {
            MenuDisplay_ParentLevelAdapter parentLevelAdapter = new MenuDisplay_ParentLevelAdapter(this, listDataHeader, DS, dhname);
            mExpandableListView.setAdapter(parentLevelAdapter);
        }

    }
}
