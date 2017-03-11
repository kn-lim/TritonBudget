package teammemes.tritonbudget.Menus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.List;

import teammemes.tritonbudget.DiningHallSelection;
import teammemes.tritonbudget.Help;
import teammemes.tritonbudget.History;
import teammemes.tritonbudget.HomeScreen;
import teammemes.tritonbudget.R;
import teammemes.tritonbudget.Settings;
import teammemes.tritonbudget.Statistics;
import teammemes.tritonbudget.User;
import teammemes.tritonbudget.db.MenuDataSource;

public class MenuRoots_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    /*For the Menu*/
    public MenuDataSource DS;
    String dhname = "Roots";
    /*For the Navigation Drawer*/
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private User usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_menu); //change this to the drawer

        /*This sets up the navigation drawers*/
        usr = User.getInstance(getApplicationContext());

        //Creates the toolbar to the one defined in nav_action
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Roots");

        //Create the Drawer layout and the toggle
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_menu);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        //Create the navigationView and add a listener to listen for menu selections
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.getHeaderView(0);
        TextView usrName = (TextView) navHeaderView.findViewById(R.id.header_name);
        usrName.setText(usr.getName());
        /*End setting up navigation drawers*/

        // Init top level data
        DS = new MenuDataSource(this);

        //
        /*
        List<String> listDataHeader = new ArrayList<>();
        String[] mItemHeaders = new String[8];
        //String[] mItemHeaders = getResources().getStringArray(R.array.items_array_expandable_level_one);

        mItemHeaders[1] = "Root Sum";
        mItemHeaders[2] = "Root Slab";
        mItemHeaders[3] = "Field and Farm";
        mItemHeaders[4] = "Soup and Chili";
        mItemHeaders[5] = "Sides";
        mItemHeaders[6] = "Smoothies";
        mItemHeaders[7] = "Dessert";

        Collections.addAll(listDataHeader, mItemHeaders);
        */
        List<String> listCategories ;
        listCategories = DS.getCategoriesByLocation(dhname);

        final ExpandableListView mExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView_Parent);
        if (mExpandableListView != null) {

            //MenuDisplay_ParentLevelAdapter parentLevelAdapter = new MenuDisplay_ParentLevelAdapter(this, listDataHeader, mListData_SecondLevel_Map);
            MenuDisplay_ParentLevelAdapter parentLevelAdapter = new MenuDisplay_ParentLevelAdapter(this, listCategories, DS,dhname);
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

    //This method is used to listen for the user clicking the menu button, and opens
    //the drawer up
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    } //End of onOptionsItemsSelected

    //This method is used to see if the back button was pressed while the drawer was open.
    //If it is open and the back button is pressed, then close the drawer.
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }//End of onBackPress

    // This method is used to react when the user presses one of the options in the drawer

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Gets the id of the item that was selected
        int id = item.getItemId();
        Intent nextScreen;

        //Reacts to the item selected depending on which was pressed
        //Creates a new Intent for the new page and starts that activity
        switch (id) {
            case R.id.nav_home:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, HomeScreen.class);
                nextScreen.putExtra("FROM", "Menu");
                startActivity(nextScreen);
                return true;
            case R.id.nav_history:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, History.class);
                nextScreen.putExtra("FROM", "Menu");
                startActivity(nextScreen);
                return true;
            case R.id.nav_statistics:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, Statistics.class);
                nextScreen.putExtra("FROM", "Menu");
                startActivity(nextScreen);
                return true;
            case R.id.nav_menus:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, DiningHallSelection.class);
                nextScreen.putExtra("FROM", "Menu");
                startActivity(nextScreen);
                return true;
            case R.id.nav_settings:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, Settings.class);
                nextScreen.putExtra("FROM", "Menu");
                startActivity(nextScreen);
                return true;
            case R.id.nav_help:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, Help.class);
                nextScreen.putExtra("FROM", "Menu");
                startActivity(nextScreen);
                return true;
            default:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
        }

    }
}