package teammemes.tritonbudget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import teammemes.tritonbudget.db.HistoryDataSource;
import teammemes.tritonbudget.db.TranHistory;

import static android.graphics.Color.rgb;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    final Context context = this;
    private User usr;
    private TextView totBal;
    private TextView dailyRBal;
    private HistoryDataSource database;


    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_home_screen);

        database = new HistoryDataSource(this);

        /*User you=User.getInstance(getApplicationContext());
        */
        usr = User.getInstance(getApplicationContext());


        //Creates the toolbar to the one defined in nav_action
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Home");


        //Create the Drawer layout and the toggle
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_home_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();


        //Create the navigationView and add a listener to listen for menu selections
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView= navigationView.getHeaderView(0);
        TextView usrName = (TextView) navHeaderView.findViewById(R.id.header_name);
        usrName.setText(usr.getName());

        renderBalances();


        /*End Format the total balance*/

        /*
        Format the Daily Remaining Budget
        decimalIdx = dailyRemain.indexOf('.');
        //Edge case, where balance == $XXX.00, it wrongly displays one instance of 0. This fixes it.
        if (decimalIdx + 1 == usrtotBal.length() - 1) {
            usrtotBal = usrtotBal + "0";
        }
        dollarStr = dailyRemain.substring(0, decimalIdx);
        centStr = dailyRemain.substring(decimalIdx, dailyRemain.length());
        dollars = new SpannableString(dollarStr);
        cents = new SpannableString(centStr);
        ftsize = new RelativeSizeSpan((float) 3.00);
        ftsize2 = new RelativeSizeSpan((float) 1.75);
        colorDol = new ForegroundColorSpan(Color.BLACK);
        colorCents = new ForegroundColorSpan(Color.BLACK);
        dollars.setSpan(ftsize, 0, dollarStr.length(), 0);
        cents.setSpan(ftsize2, 0, centStr.length(), 0);
        dollars.setSpan(colorDol, 0, dollarStr.length(), 0);
        cents.setSpan(colorCents, 0, centStr.length(), 0);
        dailyRBal.setText(TextUtils.concat(dollars, cents));
        End Format the Daily Remaining Budget
*/

        /*Colors the Button to Custom GOLD*/
        Button deductbtn = (Button) findViewById(R.id.HS_Button_Deduct);
        Button purchasebtn = (Button) findViewById(R.id.HS_Button_Purchase);

        deductbtn.setBackgroundColor(rgb(255, 235, 59));
        purchasebtn.setBackgroundColor(rgb(255, 235, 59));
        /*End Colors the Button to Custom GOLD*/

        //Adds a special click listener for the deduct btn
        deductbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Deduction");

                LayoutInflater viewInflated = LayoutInflater.from(context);
                View deductView = viewInflated.inflate(R.layout.dialog_deduction,null);
                // Set up the input

                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                builder.setView(deductView);

                final EditText input = (EditText) deductView.findViewById(R.id.deduct_input);

                //This TextChangedListener is used to stop the user from inputing more than two decimal points
                input.addTextChangedListener(new TextWatcher() {
                    //Two methods needed to create new TextWatcher
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    //After the text is changed this method removes the extra characters if any
                    public void afterTextChanged(Editable s) {
                        String temp = s.toString();
                        int posDot = temp.indexOf(".");
                        if (posDot <= 0) {
                            return;
                        }
                        if (temp.length() - posDot - 1 > 2) {
                            s.delete(posDot + 3, s.length()-1);
                        }
                    }
                });

                // Set up the buttons
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Gets the input value and then deducts the balance and updates the balances
                        //on the Home Screen
                        String value= input.getText().toString();
                        deductBalance((double) Double.parseDouble(value));
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        purchasebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DiningHallSelection.class);
                intent.putExtra("FROM", "PURCHASE");
                startActivity(intent);
            }
        });
        /* TODO: ADD ON BUTTON LISTENERS*/
        TextView daily = (TextView)findViewById(R.id.HS_TextView_DailyBudgetValue);
        double daysp=usr.getBalance()/(167- Calendar.getInstance().get(Calendar.DAY_OF_YEAR))*100;
        daysp=Math.round(daysp);
        daysp=daysp/100;
        daily.setText("$"+Double.toString(daysp));
    }

    /*
     * Method: renderBalances
     *
     * This method is used to render the balances on the home screen. It is called whenever the
     * value of the users balance is updated, and when the HomeScreen is opened.
     */
    private void renderBalances() {
        //Gets the ID of TextViews
        totBal = (TextView) findViewById(R.id.HS_TextView_BalanceValue);
        dailyRBal = (TextView) findViewById(R.id.HS_TextView_DailyBudgetValue);

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
        //BLU = 2 136 209
        ForegroundColorSpan colorDol = new ForegroundColorSpan(rgb(3,169,244));
        ForegroundColorSpan colorCents = new ForegroundColorSpan(rgb(3,169,244));

        dollars.setSpan(colorDol, 0, dollarStr.length(), 0);
        cents.setSpan(colorCents, 0, centStr.length(), 0);
        totBal.setText(TextUtils.concat(dollars, cents));
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

    /*
     * Method: deductBalance
     *
     * Parameters: deduction - the amount that wants to be deducted from the users balance
     *
     * This method gets the user's balance and subtracts the deduction. If the deduction is more
     * than the remaining balance, then it shows an error message. Otherwise it creates and adds
     * a new transaction and sets the user's balance.
     */
    public void deductBalance(double deduction){
        double balance = usr.getBalance();
        balance -= deduction;

        if (balance < 0) {
            Toast.makeText(this, "Cannot deduct more than remaining budget.", Toast.LENGTH_LONG).show();
            return;
        }
        else {
            TranHistory transaction = new TranHistory(1,"Deduction",1,new Date(), deduction);
            database.createTransaction(transaction);

            Toast.makeText(this,"Deducted $" + deduction, Toast.LENGTH_LONG).show();

            usr.setBalance(balance);
            renderBalances();
        }
   }

    //This method is used to see if the back button was pressed while the drawer was open.
    //If it is open and the back button is pressed, then close the drawer.
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
         mDrawerLayout.closeDrawer(GravityCompat.START);
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
                return true;
            case R.id.nav_history:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, History.class);
                nextScreen.putExtra("FROM", "Home");
                startActivity(nextScreen);
                return false;
            case R.id.nav_statistics:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, Statistics.class);
                nextScreen.putExtra("FROM", "Home");
                startActivity(nextScreen);
                return true;
            case R.id.nav_menus:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, DiningHallSelection.class);
                nextScreen.putExtra("FROM", "Home");
                startActivity(nextScreen);
                return true;
            case R.id.nav_settings:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, Settings.class);
                nextScreen.putExtra("FROM", "Home");
                startActivity(nextScreen);
                return true;
            case R.id.nav_help:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, Help.class);
                nextScreen.putExtra("FROM", "Home");
                startActivity(nextScreen);
                return true;
            default:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
        }
    }
}
