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
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import static android.graphics.Color.rgb;
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

        /*TODO: Have first time user logic here */

        //Gets the ID of TextViews
        TextView totBal = (TextView) findViewById(R.id.HS_TextView_BalanceValue);
        TextView dailyRBal = (TextView) findViewById(R.id.HS_TextView_DailyBudgetValue);

        /*TODO: replace user and balance with the local account! This is merely for front end!*/
        //For Front-end Formatting purposes, please remove later
        double balance = 800.00;
        User usr = new User("User", balance, "0");
        String dailyRemain = "$16.00";
        //End TODO

        //Gets the balance from the user
        String usrtotBal = "$" + usr.getBalance();
        int decimalIdx = usrtotBal.indexOf('.');
        //Edge case, where balance == $XXX.00, it wrongly displays one instance of 0. This fixes it.
        if (decimalIdx + 1 == usrtotBal.length() - 1) {
            usrtotBal = usrtotBal + "0";
        }

        /*Format the total balance*/
        //Split between Dollars and Cents
        String dollarStr = usrtotBal.substring(0, decimalIdx);
        SpannableString dollars = new SpannableString(dollarStr);

        String centStr = usrtotBal.substring(decimalIdx, usrtotBal.length());
        SpannableString cents = new SpannableString(centStr);
        //Formats the font size
        RelativeSizeSpan ftsize = new RelativeSizeSpan((float) 5.0);
        RelativeSizeSpan ftsize2 = new RelativeSizeSpan((float) 2.0);

        dollars.setSpan(ftsize, 0, dollarStr.length(), 0);
        cents.setSpan(ftsize2, 0, centStr.length(), 0);

        //Formats the color, gold = RGB 255 235 59 | HSV 54 77 100 | FFEB3B
        //Green = 113 197 144 | 142 43 77 | 71C590
        ForegroundColorSpan colorDol = new ForegroundColorSpan(rgb(113, 197, 144));
        ForegroundColorSpan colorCents = new ForegroundColorSpan(rgb(113, 197, 144));

        dollars.setSpan(colorDol, 0, dollarStr.length(), 0);
        cents.setSpan(colorCents, 0, centStr.length(), 0);
        totBal.setText(TextUtils.concat(dollars, cents));
        /*End Format the total balance*/

        /*Format the Daily Remaining Budget*/
        decimalIdx = dailyRemain.indexOf('.');
        //Edge case, where balance == $XXX.00, it wrongly displays one instance of 0. This fixes it.
        if (decimalIdx + 1 == usrtotBal.length() - 1) {
            usrtotBal = usrtotBal + "0";
        }
        dollarStr = dailyRemain.substring(0, decimalIdx);
        centStr = dailyRemain.substring(decimalIdx, dailyRemain.length());
        dollars = new SpannableString(dollarStr);
        cents = new SpannableString(centStr);
        ftsize = new RelativeSizeSpan((float) 2.5);
        ftsize2 = new RelativeSizeSpan((float) 1.0);
        colorDol = new ForegroundColorSpan(Color.BLACK);
        colorCents = new ForegroundColorSpan(Color.BLACK);
        dollars.setSpan(ftsize, 0, dollarStr.length(), 0);
        cents.setSpan(ftsize2, 0, centStr.length(), 0);
        dollars.setSpan(colorDol, 0, dollarStr.length(), 0);
        cents.setSpan(colorCents, 0, centStr.length(), 0);
        dailyRBal.setText(TextUtils.concat(dollars, cents));
        /*End Format the Daily Remaining Budget*/

        /*Colors the Button to Custom GOLD*/
        Button deductbtn = (Button) findViewById(R.id.HS_Button_Deduct);
        Button purchasebtn = (Button) findViewById(R.id.HS_Button_Purchase);

        deductbtn.setBackgroundColor(rgb(255, 235, 59));
        purchasebtn.setBackgroundColor(rgb(255, 235, 59));
        /*End Colors the Button to Custom GOLD*/

        /* TODO: ADD ON BUTTON LISTENERS*/

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