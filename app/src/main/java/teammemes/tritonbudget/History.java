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

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
    LinearLayout.LayoutParams layoutParams, textParams, btnParams;
    LinearLayout mainLayout;
    List<TranHistory> transactions;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_history);
        context = getBaseContext();

        //Gets the user object
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

        //Get the navigation drawer header and set's the name to user's name
        View navHeaderView= navigationView.getHeaderView(0);
        TextView usrName = (TextView) navHeaderView.findViewById(R.id.header_name);
        usrName.setText(usr.getName());

        //The two sets of layout parameters that are used to make the transactions
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        btnParams = new LinearLayout.LayoutParams(200, 100);
        btnParams.setMargins(0,0,0,40);

        //Fetches the main empty layout
        mainLayout = (LinearLayout) findViewById(R.id.page);


        //Used for when database is working
        database = new HistoryDataSource(this);
        transactions = database.getAllTransaction();
        Calendar oldDate = (Calendar) Calendar.getInstance().clone();
        oldDate.add(Calendar.DATE, -8);
        transactions.add(new TranHistory(1, "Week meal", 1, oldDate.getTime(), 8999));
        oldDate.add(Calendar.MONTH, -1);
        System.out.println(oldDate.getTime().toString());
        transactions.add(new TranHistory(1, "Month meal", 1, oldDate.getTime(), 9001));
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        //Renders all of the transactions on the page
        renderTransactions(transactions, "week");

    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.history_menu, menu);
        return true;
    }

    /*
     * Method: renderTransactions
     *
     * Parameters: transactions - The List of Transactions returned by the database
     *
     * This method is used to render all of the transactions onto the history page.
     * If there are no transactions it adds a default message, however if there are
     * transactions it goes through each one putting them in the mainLayout.
     */
    private void renderTransactions(final List<TranHistory> transactions, String duration) {
        //Resets the mainLayout
        mainLayout.removeAllViews();

        //If there are no previous transactions, display the message
        if (transactions.size() == 0){
            TextView textView = new TextView(this);
            textView.setText("No Transaction History to Display");
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.GRAY);
            textView.setTextSize(50);
            textView.setPadding(8, 400, 8, 8);
            textView.setTextColor(textView.getTextColors().withAlpha(64));
            textView.setLayoutParams(layoutParams);
            mainLayout.addView(textView);
        }

        Calendar endDate = (Calendar) Calendar.getInstance().clone();
        if (duration.equals("week")){
            endDate.add(Calendar.DATE, -7);
            System.out.println(endDate.getTime());
        }
        else if (duration.equals("month")){
            endDate.add(Calendar.MONTH, -1);
        }
        else if (duration.equals("quarter")){
            endDate.set(2017,0,1);
            System.out.println(endDate.getTime().toString());
        }

        Date comparison = endDate.getTime();

        //Goes through each of the transactions and puts them on the page
        String prevDate = "";
        Calendar transCal = (Calendar) Calendar.getInstance().clone();
        for (int i = transactions.size() - 1; i >= 0; i--){
            //If this is a new date, it creates a new date display
            TranHistory transaction = transactions.get(i);
            transCal = (Calendar) Calendar.getInstance().clone();
            transCal.setTime(transactions.get(i).getTdate());
            Date transDate = transCal.getTime();

            if (transDate.before(comparison))
                continue;

            if(!dateFormat.format(transactions.get(i).getTdate()).equals(prevDate)){
                prevDate = dateFormat.format(transactions.get(i).getTdate()); //Saves the date

                LinearLayout DateBorder = new LinearLayout(this);
                DateBorder.setBackgroundResource(R.drawable.border_set_top);
                DateBorder.setOrientation(LinearLayout.HORIZONTAL);
                DateBorder.setLayoutParams(layoutParams);

                //Creates the date_display and adds it to the page
                TextView date_display = new TextView(this);
                date_display.setGravity(CENTER);
                date_display.setPaddingRelative(8,8,8,8);
                date_display.setPadding(8,8,8,8);
                date_display.setText(prevDate);
                date_display.setTextSize(20);
                date_display.setLayoutParams(layoutParams);
                DateBorder.addView(date_display);
                mainLayout.addView(DateBorder);
            }

            final LinearLayout TransactionBorder = new LinearLayout(this);
            TransactionBorder.setOrientation(LinearLayout.HORIZONTAL);
            TransactionBorder.setLayoutParams(layoutParams);
            TransactionBorder.setBackgroundResource(R.drawable.border_set_top);

            TextView name_display = new TextView(this);
            name_display.setPaddingRelative(8,8,8,8);
            name_display.setPadding(8,8,8,8);
            name_display.setText(transactions.get(i).getName());
            name_display.setTextSize(20);
            name_display.setLayoutParams(textParams);

            TextView cost_display = new TextView(this);
            cost_display.setPaddingRelative(8,8,8,8);
            cost_display.setPadding(8,8,15,8);
            if (!transactions.get(i).getName().equals("Added Dining Dollars")) {
                cost_display.setTextColor(Color.RED);
            }
            else {
                cost_display.setTextColor(Color.GREEN);
            }
            cost_display.setText("$" + Double.toString(transactions.get(i).getCost()));
            cost_display.setTextSize(20);
            cost_display.setLayoutParams(textParams);
            cost_display.setGravity(Gravity.RIGHT);     //Aligns it on the right

            //Adds the two displays to the transaction line
            TransactionBorder.addView(name_display);
            TransactionBorder.addView(cost_display);

            TransactionBorder.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (TransactionBorder.getChildCount() == 3)
                        return false;
                    Button edit = new Button(v.getContext());
                    edit.setText("Edit");
                    edit.setLayoutParams(btnParams);
                    edit.setPadding(0,0,0,0);
                    edit.setTextSize(10);
                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(History.this);
                            builder.setTitle("Change Transaction Amount");

                            LayoutInflater viewInflated = LayoutInflater.from(context);
                            View deductView = viewInflated.inflate(R.layout.dialog_deduction,null);
                            // Set up the input

                            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                            builder.setView(deductView);

                            final EditText input = (EditText) deductView.findViewById(R.id.deduct_input);
                            //input.setText((String) transaction.getCost());

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
                                        s.delete(posDot + 3, posDot + 4);
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
                                }
                            });
                            builder.setNeutralButton("Delete",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
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
                    TransactionBorder.addView(edit);
                    return true;
                }
            });
            //If it is the last transaction it creates the bottom border.
            if (i == 0)
                TransactionBorder.setBackgroundResource(R.drawable.border_set_top_bottom);

            //Adds the transaction to the main page
            mainLayout.addView(TransactionBorder);

        }
    }

    //This method is used to listen for the user clicking the menu button, and opens
    //the drawer up
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.show_week) {
            renderTransactions(transactions,"week");
            return true;
        }
        else if (id == R.id.show_month){
            renderTransactions(transactions,"month");
            return true;
        }
        else if (id == R.id.show_quarter){
            renderTransactions(transactions, "quarter");
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

