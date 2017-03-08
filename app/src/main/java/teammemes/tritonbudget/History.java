package teammemes.tritonbudget;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import teammemes.tritonbudget.db.HistoryDataSource;
import teammemes.tritonbudget.db.TranHistory;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static android.view.Gravity.CENTER;


public class History extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private User usr;
    private HistoryDataSource database;
    SimpleDateFormat dateFormat;
    LinearLayout.LayoutParams layoutParams, textParams;
    LinearLayout page;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_history);



        usr = User.getInstance(getApplicationContext());

        //Creates the toolbar to the one defined in nav_action
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("History");


        //Create the Drawer layout and the toggle
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_history_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();


        //Create the navigationView and add a listener to listen for menu selections
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView= navigationView.getHeaderView(0);
        TextView usrName = (TextView) navHeaderView.findViewById(R.id.header_name);
        usrName.setText(usr.getName());



        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        page = (LinearLayout) findViewById(R.id.page);


        //Used for when database is working
        database = new HistoryDataSource(this);
        List<TranHistory> transactions = database.getAllTransaction();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        renderTransactions(transactions);

    }

    private void renderTransactions(List<TranHistory> transactions) {
        if (transactions.size() == 0){
            TextView textView = new TextView(this);
            textView.setText("No Transaction History to Display");
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.GRAY);
            textView.setTextSize(50);
            textView.setPadding(8, 400, 8, 8);
            textView.setTextColor(textView.getTextColors().withAlpha(64));
            textView.setLayoutParams(layoutParams);
            page.addView(textView);
        }

        String prevDate = "";
        for (int i = transactions.size() - 1; i >= 0; i--){
            if(!dateFormat.format(transactions.get(i).getTdate()).equals(prevDate)){
                prevDate = dateFormat.format(transactions.get(i).getTdate());

                LinearLayout DateBorder = new LinearLayout(this);
                DateBorder.setBackgroundResource(R.drawable.border_set_top);
                DateBorder.setOrientation(LinearLayout.HORIZONTAL);
                DateBorder.setLayoutParams(layoutParams);


                TextView date_display = new TextView(this);
                date_display.setGravity(CENTER);
                date_display.setPaddingRelative(8,8,8,8);
                date_display.setPadding(8,8,8,8);
                date_display.setText(prevDate);
                date_display.setLayoutParams(textParams);
                DateBorder.addView(date_display);
                page.addView(DateBorder);
            }

            LinearLayout DateBorder = new LinearLayout(this);
            DateBorder.setOrientation(LinearLayout.HORIZONTAL);
            DateBorder.setLayoutParams(layoutParams);
            DateBorder.setBackgroundResource(R.drawable.border_set_top);

            TextView date_display = new TextView(this);
            date_display.setPaddingRelative(8,8,8,8);
            date_display.setPadding(8,8,8,8);
            date_display.setText("$" + Double.toString(transactions.get(i).getCost()));
            date_display.setLayoutParams(textParams);
            date_display.setLineSpacing((4/getApplicationContext().getResources().getDisplayMetrics().density), 1);
            DateBorder.addView(date_display);

            if (i == 0)
                DateBorder.setBackgroundResource(R.drawable.border_set_top_bottom);
            page.addView(DateBorder);

        }
    }

    private int id;
    private int menuId;
    private int quantity;
    private Date tdate;
    private double cost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getTdate() {
        return tdate;
    }

    public void setTdate(Date tdate) {
        this.tdate = tdate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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
        Intent nextScreen;

        //Reacts to the item selected depending on which was pressed
        //Creates a new Intent for the new page and starts that activity
        switch (id) {
            case R.id.nav_home:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, HomeScreen.class);
                nextScreen.putExtra("FROM", "History");
                startActivity(nextScreen);
                return true;
            case R.id.nav_history:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            case R.id.nav_statistics:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, Statistics.class);
                nextScreen.putExtra("FROM", "History");
                startActivity(nextScreen);
                return true;
            case R.id.nav_menus:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, DiningHallSelection.class);
                nextScreen.putExtra("FROM", "History");
                startActivity(nextScreen);
                return true;
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
    }
}

