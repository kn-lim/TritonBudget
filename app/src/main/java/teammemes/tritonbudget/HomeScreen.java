package teammemes.tritonbudget;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_home_screen);

        //Creates the toolbar to the one defined in nav_action
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Create the Drawer layout and the toggle
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_home_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();


        //Create the navigationView and add a listener to listen for menu selections
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //This method is used to listen for the user clicking the menu button, and opens
    //the drawer up
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //This method is used to see if the back button was pressed while the drawer was open.
    //If it is open and the back button is pressed, then close the drawer.
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // This method is used to react when the user presses one of the options in the drawer
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Gets the id of the item that was selected
        int id = item.getItemId();

        //Reacts to the item selected depending on which was pressed
        //Creates a new Intent for the new page and starts that activity
        switch (id) {
            case R.id.nav_home:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_history:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            case R.id.nav_statistics:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(this, Statistics.class);
                intent.putExtra(EXTRA_MESSAGE, "From Home");
                startActivity(intent);
                return true;
            case R.id.nav_menus:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            /* Cases for future options
            case R.id.nav_settings:
                return false;
            case R.id.nav_help:
                return false:
            */
            default:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
        }

        /*
        if (id == R.id.nav_home) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else if (id == R.id.nav_history) {
            mDrawerLayout.closeDrawer(GravityCompat.START);

        } else if (id == R.id.nav_statistics) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(this, Statistics.class);
            intent.putExtra(EXTRA_MESSAGE, "From Home");
            startActivity(intent);

            return true;
        } else if (id == R.id.nav_menus) {

        } /*else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_help) {

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;*/
    }
}