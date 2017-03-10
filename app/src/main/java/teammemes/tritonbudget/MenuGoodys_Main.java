package teammemes.tritonbudget;

/**
 * Created by Ziying on 3/8/17.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import teammemes.tritonbudget.db.MenuDataSource;

public class MenuGoodys_Main extends AppCompatActivity {
    public MenuDataSource DS;
    String dhname = "Goodys";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_display_main);
        // Init top level data

        DS = new MenuDataSource(this);

        //
        /*
        List<String> listDataHeader = new ArrayList<>();
        String[] mItemHeaders = new String[4];

        mItemHeaders[1] = "Breakfast Specials";
        mItemHeaders[2] = "Latin";
        mItemHeaders[3] = "Sides";

        Collections.addAll(listDataHeader, mItemHeaders);
        */
        List<String> listCategories ;
        listCategories = DS.getCategoriesByLocation(dhname);

        final ExpandableListView mExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView_Parent);
        if(mExpandableListView!=null) {
            MenuDisplay_ParentLevelAdapter parentLevelAdapter = new MenuDisplay_ParentLevelAdapter(this, listCategories, DS, dhname);
            mExpandableListView.setAdapter(parentLevelAdapter);
        }

}

}
