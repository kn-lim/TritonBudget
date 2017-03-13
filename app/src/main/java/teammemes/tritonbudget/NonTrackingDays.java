package teammemes.tritonbudget;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import static android.view.Gravity.CENTER;
import static android.view.Gravity.RIGHT;

public class NonTrackingDays extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<String> noneatingdays = new ArrayList<>();
    int id = 100;
    LinearLayout.LayoutParams layoutParams, textParams, btnParams, costParams;
    LinearLayout mainLayout;
    private DatePicker datePicker;
    private Calendar calendar;
    private int inityear, initmonth, initday;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private User usr;
    private TextView usrName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_nontracking);

        usr = User.getInstance(getApplicationContext());

        /*Navigation Drawer stuff*/
        //Creates the toolbar to the one defined in nav_action
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");

        //Create the Drawer layout and the toggle
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_nontracking);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        //Create the navigationView and add a listener to listen for menu selections
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.getHeaderView(0);
        usrName = (TextView) navHeaderView.findViewById(R.id.header_name);
        usrName.setText(usr.getName());


        /* Begin Date Picker Stuff*/
        calendar = Calendar.getInstance();
        inityear = calendar.get(Calendar.YEAR);
        initmonth = calendar.get(Calendar.MONTH);
        initday = calendar.get(Calendar.DAY_OF_MONTH);

        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        costParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        btnParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);  //was (200,100)
        btnParams.setMargins(0, 0, 0, 0); //was (0,0,0,40)


        //Collections.sort(usr.getNon_tracking_days());
        render_non_eating(usr.getNon_tracking_days());
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        datePicker.init(inityear, initmonth, initday, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int curryear, int monthOfYear, int dayOfMonth) {

                //if (curryear <= inityear+1) { //Only check if current year and next year
                if (curryear == inityear) { //Only if current year
                    //This should occur only when Date < June 16 2017
                    if ((monthOfYear > initmonth && monthOfYear < 5)) { //Takes care of months after current and before june
                        addToNonEating(curryear, monthOfYear, dayOfMonth);
                    } else if (monthOfYear == initmonth && initday <= dayOfMonth) { //SAME MONTH
                        //Toast.makeText(getApplicationContext(), "Same", Toast.LENGTH_SHORT).show();
                        addToNonEating(curryear, monthOfYear, dayOfMonth);
                    } else if (monthOfYear == 5 && dayOfMonth < 17) { //MONTH OF JUNE
                        //Toast.makeText(getApplicationContext(), "Month of june!", Toast.LENGTH_SHORT).show();
                        addToNonEating(curryear, monthOfYear, dayOfMonth);
                    } else {
                        //Do Nothing
                    }
                }
                if (curryear < inityear) {
                    Toast.makeText(getApplicationContext(), "Day already occurred!", Toast.LENGTH_SHORT).show();
                } else if (curryear == inityear) {
                    if (monthOfYear < initmonth) { //Previous month, same year
                        Toast.makeText(getApplicationContext(), "Day already occurred!", Toast.LENGTH_SHORT).show();
                    } else if (monthOfYear == initmonth && dayOfMonth < initday) { //Same month, old day
                        Toast.makeText(getApplicationContext(), "Day already occurred!", Toast.LENGTH_SHORT).show();
                    } else if (monthOfYear >= 5 && dayOfMonth > 16) { //June
                        Toast.makeText(getApplicationContext(), "School is over!", Toast.LENGTH_SHORT).show();
                    } else {
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "School is over!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                nextScreen.putExtra("FROM", "Settings");
                startActivity(nextScreen);
                return true;
            case R.id.nav_history:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, History.class);
                nextScreen.putExtra("FROM", "Settings");
                startActivity(nextScreen);
                return true;
            case R.id.nav_statistics:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, Statistics.class);
                nextScreen.putExtra("FROM", "Settings");
                startActivity(nextScreen);
                return true;
            case R.id.nav_menus:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, DiningHallSelection.class);
                nextScreen.putExtra("FROM", "Settings");
                startActivity(nextScreen);
                return true;
            case R.id.nav_settings:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_help:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, Help.class);
                nextScreen.putExtra("FROM", "Settings");
                startActivity(nextScreen);
                return true;
            default:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
        }
    }

    private void showDate(int year, int month, int day) {
        String str = " " + new StringBuilder().append(month).append("/").append(day).append("/").append(year);
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        TextView test = (TextView) findViewById(R.id.NED_TV_LIST);
        noneatingdays = usr.getNon_tracking_days();
        test.setText("Dates already accounted for:\n");

        Collections.sort(noneatingdays);
        for (int i = 0; i < noneatingdays.size(); i++) {
            test.append(" " + noneatingdays.get(i));
        }

    }

    private void addToNonEating(int curryear, int monthOfYear, int dayOfMonth) {
        String date_noeat = curryear + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
        if (dayOfMonth < 10) {
            date_noeat = curryear + "/" + (monthOfYear + 1) + "/0" + dayOfMonth;
        }
        noneatingdays = usr.getNon_tracking_days();
        if (!noneatingdays.contains(date_noeat)) {
            usr.setNon_tracking_days(date_noeat);

            render_non_eating(noneatingdays);
        } else {
            Toast.makeText(getApplicationContext(), "Already accounted for!", Toast.LENGTH_SHORT).show();
        }
    }

    private void render_non_eating(final ArrayList<String> NED) {
        int transactionsShown = 0;
        Collections.sort(NED);
        //Get the mainLayout where a bunch of horizontal layouts are going to be stored
        mainLayout = (LinearLayout) findViewById(R.id.NED_LLV_DATES);
        //Resets the mainLayout
        mainLayout.removeAllViews();

        //Goes through each of the dates and puts them on the page
        for (int i = NED.size() - 1; i >= 0; i--) {
            //Get the STRING of the DATE to add into a textview
            String str_to_display = NED.get(i);

            //THIS IS THE "date_border" to have an edit button later on
            final LinearLayout date_border = new LinearLayout(this);
            //Actually contains the date stuff
            final LinearLayout date_container = new LinearLayout(this);

            //Date Border properties
            date_border.setBackgroundResource(R.drawable.border_set_top);
            date_border.setOrientation(LinearLayout.HORIZONTAL);
            date_border.setLayoutParams(layoutParams);
            date_border.setPadding(4, 0, 0, 0);

            //Date Container Properties
            TypedValue outValue = new TypedValue();
            this.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
            date_container.setBackgroundResource(outValue.resourceId);
            date_container.setOrientation(LinearLayout.HORIZONTAL);
            date_container.setLayoutParams(layoutParams);

            //Creates the date_display textview and adds it to "date_border"
            final TextView date_display = new TextView(this);
            date_display.setGravity(CENTER);
            date_display.setPaddingRelative(8, 8, 8, 8);
            date_display.setPadding(15, 15, 15, 15);
            date_display.setText(str_to_display);
            date_display.setTextSize(26);
            date_display.setLayoutParams(textParams);

            //Add the textview to the date_container
            date_container.addView(date_display);
            //add the container to the border
            date_border.addView(date_container);
            //add the date_border to the "main_layout"
            mainLayout.addView(date_border);


            //Creates the onLongClickListener for the transaction:
            //      If the transaction is held an edit button appears
            final LinearLayout edit_container = new LinearLayout(this);
            edit_container.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            edit_container.setWeightSum(1);

            date_container.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //If the edit button is already visible do nothing
                    if (date_container.getChildCount() >= 2) {
                        // System.out.println("hello");
                        // System.out.print(date_border.getChildCount()>=3);
                        return false;
                    }
                    removeAllButtons();

                    //Add another LLH to push the edit button to the right
                    edit_container.setGravity(RIGHT);
                    date_container.addView(edit_container);
                    //Create the edit button
                    TextView edit = new TextView(v.getContext());
                    edit.setText("Delete");
                    edit.setBackgroundColor(Color.RED);
                    edit.setTextColor(Color.WHITE);
                    edit.setLayoutParams(btnParams);
                    edit.setPadding(0, 0, 0, 0);
                    edit.setTextSize(26);
                    edit.setGravity(Gravity.CENTER);

                    //Set the onClickListener for the button:
                    //      If the edit button is clicked, it brings up a dialog allowing the user
                    //      to edit the transaction
                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Set up the buttons
                            //Removes the date

                            //This is fucking up

                            //NED IS THE ARRAYLIST
                            String toRemove = (String) date_display.getText();

                            //System.out.println(toRemove);
                            NED.remove(toRemove);
                            usr.remove_days(toRemove);

                            ArrayList<String> potato = usr.getNon_tracking_days();
                            //System.out.println(potato.contains(toRemove));
                            render_non_eating(NED);
                        }
                    });
                    if (edit_container.getChildCount() < 1) {
                        edit_container.addView(edit);
                    }
                    return true;
                }
            });
            //If it is the last one, create the bottom border.
            if (i == 0) {
                date_border.setBackgroundResource(R.drawable.border_set_top_bottom);

                //Adds the date to the main page
                //mainLayout.addView(date_border);
            }
            transactionsShown++;
        }
        if(transactionsShown==0){
            ImageView meme = new ImageView(this);
            meme.setImageResource(R.drawable.ramen);
            meme.setAdjustViewBounds(true);
            meme.setScaleType(ImageView.ScaleType.FIT_END);
            meme.setPadding(0, 200, 0, 0);
            meme.setAlpha((float) 0.75);
            mainLayout.addView(meme);
        }
    }

    private void removeAllButtons() {
        int numTrans = mainLayout.getChildCount();
        for (int j = 0; j < numTrans; j++) {
            LinearLayout date_border = (LinearLayout) mainLayout.getChildAt(j);
            LinearLayout date_container = (LinearLayout) date_border.getChildAt(0);
            if (date_container.getChildCount() == 2) {
                date_container.removeViewAt(1);
            }
        }
    }

}