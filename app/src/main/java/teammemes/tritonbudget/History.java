package teammemes.tritonbudget;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import teammemes.tritonbudget.db.HistoryDataSource;
import teammemes.tritonbudget.db.TranHistory;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static android.view.Gravity.CENTER;
import static android.widget.ImageView.ScaleType.FIT_END;


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
    HashMap<Integer ,TranHistory> historyHashMap;
    int id = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_history);
        context = getBaseContext();
        historyHashMap = new HashMap<Integer, TranHistory>();

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
        btnParams = new LinearLayout.LayoutParams(200, ViewGroup.LayoutParams.MATCH_PARENT);  //was (200,100)
        btnParams.setMargins(0,0,0,0); //was (0,0,0,40)

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
     *             duration - this is the variable that specifies which transactions to show
     *
     * This method is used to render all of the transactions onto the history page.
     * If there are no transactions it adds a default message, however if there are
     * transactions it goes through each one putting them in the mainLayout.
     */
    private void renderTransactions(final List<TranHistory> transactions, final String duration) {
        int transactionsShown = 0;

        //Resets the mainLayout
        mainLayout.removeAllViews();
        historyHashMap = new HashMap<Integer, TranHistory>();

        //Creates the date that it will stop showing transactions at.
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

            //If the date is befor the last date to show skip it.
            if (transDate.before(comparison))
                continue;

            //If this is a new date, it creates a new date display
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

            //Creates the linear layout to hold the transaction
            final LinearLayout TransactionBorder = new LinearLayout(this);
            TransactionBorder.setOrientation(LinearLayout.HORIZONTAL);
            TransactionBorder.setLayoutParams(layoutParams);
            TransactionBorder.setBackgroundResource(R.drawable.border_set_top);
            TransactionBorder.setId(id++);

            //puts the transaction into the hashmap to be accessible later
            historyHashMap.put(TransactionBorder.getId(),transaction);

            //Creates the name of the transaction
            TextView name_display = new TextView(this);
            name_display.setPaddingRelative(8,8,8,8);
            name_display.setPadding(8,8,8,8);
            name_display.setText(transactions.get(i).getName());
            name_display.setTextSize(20);
            name_display.setLayoutParams(textParams);

            //Creates the cost of the transaction
            TextView cost_display = new TextView(this);
            cost_display.setPaddingRelative(8,8,8,8);
            cost_display.setPadding(8,8,15,8);
            cost_display.setText("$" + Double.toString(Math.abs(transactions.get(i).getCost())));
            cost_display.setTextSize(20);
            cost_display.setLayoutParams(textParams);
            cost_display.setGravity(Gravity.RIGHT);     //Aligns it on the right

            //Changes the color depending on whether it was added or subtracted
            if (!transactions.get(i).getName().equals("Added Dining Dollars")) {
                cost_display.setTextColor(Color.RED);
            }
            else {
                cost_display.setTextColor(Color.GREEN);
            }


            //Adds the two displays to the transaction line
            TransactionBorder.addView(name_display);
            TransactionBorder.addView(cost_display);

            //Creates the onLongClickListener for the transaction:
            //      If the transaction is held an edit button appears
            TransactionBorder.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //If the edit button is already visible do nothing
                    if (TransactionBorder.getChildCount() == 3)
                        return false;

                    //Create the edit button
                    TextView edit = new TextView(v.getContext());
                    edit.setText("Edit");
                    edit.setBackgroundColor(Color.RED);
                    edit.setTextColor(Color.WHITE);
                    edit.setLayoutParams(btnParams);
                    edit.setPadding(0,0,0,0);
                    edit.setTextSize(20);
                    edit.setGravity(Gravity.CENTER);

                    //Set the onClickListener for the button:
                    //      If the edit button is clicked, it brings up a dialog allowing the user
                    //      to edit the transaction
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
                            input.setText(Double.toString(Math.abs((historyHashMap.get(TransactionBorder.getId()).getCost()))));

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
                                    //Gets the input value and then updates the transaction
                                    String value= input.getText().toString();
                                    TranHistory toChange = historyHashMap.get(TransactionBorder.getId());
                                    double prevCost = toChange.getCost();
                                    double newCost;
                                    if (prevCost < 0) {
                                        newCost = 0 - Double.parseDouble(value);
                                    }
                                    else {
                                        newCost = Double.parseDouble(value);
                                    }
                                    deductBalance(prevCost, newCost);
                                    toChange.setCost(newCost);
                                    database.updateTranHistory(toChange);
                                    renderTransactions(transactions,duration);
                                }
                            });
                            //Removes the transaction
                            builder.setNeutralButton("Delete",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    TranHistory toChange = historyHashMap.get(TransactionBorder.getId());
                                    double prevCost = toChange.getCost();
                                    deductBalance(prevCost, 0);
                                    transactions.remove(toChange);
                                    database.deleteTransaction(toChange.getId());
                                    renderTransactions(transactions,duration);
                                    dialog.cancel();
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    renderTransactions(transactions,duration);
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
            transactionsShown++;
        }

        //If there are no previous transactions, display the message
        if (transactionsShown == 0){
            ImageView meme = new ImageView(this);
            meme.setImageResource(R.drawable.roll_safe);
            meme.setAdjustViewBounds(true);
            meme.setScaleType(ImageView.ScaleType.FIT_END);
            meme.setPadding(0,200,0,0);
            meme.setAlpha((float) 0.75);
            mainLayout.addView(meme);

            //meme.setScaleType(fitEnd);
            /*
            TextView textView = new TextView(this);
            textView.setText("No TransactionHistory\nto Display");
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.GRAY);
            textView.setTextSize(50);
            textView.setPadding(8, 400, 8, 8);
            textView.setTextColor(textView.getTextColors().withAlpha(64));
            textView.setLayoutParams(layoutParams);
            mainLayout.addView(textView);
            System.out.println("Here");
            */
            return;
        }
    }

    /*
     * Method: onOptionsItemSelected
     *
     * Parameters: item - the item on the toolbar selected
     *
     * This method listens for options being selected from the toolbar and responds accordingly.
     * If a selection is made on which history items to show then it calls the renderTransaction
     * method passing in the new selection to show the correct transactions.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();  //Gets the id of the item selected

        // Checks which option is selected and renders the appropriate transactions on the page
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

    /*
     * Method: onBackPressed
     *
     * This method is called when the user presses the back button. If the drawer is open it closes
     * it and otherwise it calls the super method
     */
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*
     * Method: onNavigationItemSelected
     *
     * Parameters: item - the navigation item selected
     *
     * This method chechks which item in the navigation menu was selected and creates the intent
     * that then starts the selected activity
     */
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
                nextScreen.putExtra("FROM", "History");
                startActivity(nextScreen);
                return true;
            case R.id.nav_help:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, Help.class);
                nextScreen.putExtra("FROM", "History");
                startActivity(nextScreen);
                return true;
            default:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
        }
    }

    /*
     * Method: deductBalance
     *
     * Parameters: prevCost - the previous cost of the transaction
     *             newCost - the new cost of the transaction
     *
     * This method gets the user's balance and subtracts the deduction. If the deduction is more
     * than the remaining balance, then it shows an error message. Otherwise it creates and adds
     * a new transaction and sets the user's balance.
     */
    public void deductBalance(double prevCost, double newCost){
        //Gets the balance and calculates the deduction
        double balance = usr.getBalance();
        double deduction = prevCost - newCost;

        // Calculates the new balance
        balance += deduction;

        //If it is an invalid balance show an error message and return
        if (balance < 0) {
            Toast.makeText(this, "Cannot deduct more than remaining budget.", Toast.LENGTH_LONG).show();
            return;
        }
        else if (prevCost < 0 && newCost == 0){
            Toast.makeText(this, "Removed addition of Dining Dollars", Toast.LENGTH_LONG).show();
            usr.setBalance(balance);
        }
        else if (prevCost < 0){
            Toast.makeText(this, "Changed Added Dining dollars amount to " + Math.abs(newCost), Toast.LENGTH_LONG).show();
            usr.setBalance(balance);
        }
        //Otherwise show that it was edited and update the users balance.
        else if (newCost == 0){
            Toast.makeText(this,"Deleted " + deduction, Toast.LENGTH_LONG).show();
            usr.setBalance(balance);
        }
        else{
            Toast.makeText(this,"Edited " + deduction, Toast.LENGTH_LONG).show();
            usr.setBalance(balance);
        }
    }
}

