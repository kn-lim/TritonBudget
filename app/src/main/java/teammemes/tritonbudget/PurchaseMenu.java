package teammemes.tritonbudget;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import teammemes.tritonbudget.Menus.Menu;
import teammemes.tritonbudget.db.MenuDataSource;

import static android.view.Gravity.CENTER;


public class PurchaseMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,Serializable{

    SimpleDateFormat dateFormat;
    LinearLayout.LayoutParams layoutParams, textParams, btnParams, noWeight;
    LinearLayout mainLayout;
    FloatingActionButton myFab;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private User usr;
    private MenuDataSource database;
    private int[] numberOfPurchase;
    private List<Menu> transactions;
    private int i=0;

    private float dX;
    private float dY;
    private int lastAction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_purchase_menu);

        //Gets the user object
        usr = User.getInstance(getApplicationContext());

        //Creates the toolbar to the one defined in nav_action
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Purchase Menu");


        //Create the Drawer layout and the toggle
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_purchase_menu_layout);
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
        noWeight = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        btnParams = new LinearLayout.LayoutParams(90, 100, 0);


        //Fetches the main empty layout
        mainLayout = (LinearLayout) findViewById(R.id.page);

        //Used for when database is working
        database = new MenuDataSource(this);
        transactions = database.getMenusByLocation(getIntent().getExtras().getString("FROM"));
        numberOfPurchase = new int[transactions.size()];
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        myFab = (FloatingActionButton) findViewById(R.id.myFAB);

        myFab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        lastAction = MotionEvent.ACTION_DOWN;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        view.setY(event.getRawY() + dY);
                        view.setX(event.getRawX() + dX);
                        lastAction = MotionEvent.ACTION_MOVE;
                        break;

                    case MotionEvent.ACTION_UP:
                        if (lastAction == MotionEvent.ACTION_DOWN) {
                            ArrayList<String> trans = new ArrayList<String>();
                            ArrayList<String> numofpur = new ArrayList<String>();
                            for (int j = 0; j < transactions.size(); j++) {
                                if (numberOfPurchase[j] != 0) {
                                    Menu men = transactions.get(j);
                                    //trans.add(new TranHistory(men.getId(),men.getName(),numberOfPurchase[j],new Date(),men.getCost()));
                                    trans.add("" + men.getId());
                                    numofpur.add(numberOfPurchase[j] + "");
                                }
                            }
                            if (trans.size() == 0) {
                                Toast.makeText(getApplicationContext(), "Please select item", Toast.LENGTH_LONG).show();
                            } else {
                                Intent checkout = new Intent(getApplicationContext(), Checkout.class);
                                checkout.putStringArrayListExtra("Transactions", trans);
                                checkout.putStringArrayListExtra("number", numofpur);
                                startActivity(checkout);
                            }
                        }
                        break;

                    default:
                        return false;
                }
                return true;
            }
        });

        //Renders all of the transactions on the page
        renderMenu(transactions);
        //mainLayout.addView(myFab);
    }

    /*
     * Method: renderMenu
     *
     * Parameters: transactions - The List of Transactions returned by the database
     *
     * This method is used to render all of the transactions onto the history page.
     * If there are no transactions it adds a default message, however if there are
     * transactions it goes through each one putting them in the mainLayout.
     */
    private void renderMenu(List<Menu> transactions) {
        //Resets the mainLayout
        mainLayout.removeAllViews();


        //Goes through each of the transactions and puts them on the page
        String prevCategory = "";
        for (i = transactions.size() - 1; i >= 0; i--){
            //If this is a new date, it creates a new date display
            if(!transactions.get(i).getCategory().equals(prevCategory)){
                prevCategory = transactions.get(i).getCategory(); //Saves the date

                LinearLayout DateBorder = new LinearLayout(this);
                DateBorder.setBackgroundResource(R.drawable.border_set_top);
                DateBorder.setOrientation(LinearLayout.HORIZONTAL);
                DateBorder.setLayoutParams(layoutParams);

                //Creates the date_display and adds it to the page
                TextView date_display = new TextView(this);
                date_display.setGravity(CENTER);
                date_display.setPaddingRelative(8,8,8,8);
                date_display.setPadding(8,8,8,8);
                date_display.setText(prevCategory);
                date_display.setTextSize(20);
                date_display.setLayoutParams(layoutParams);
                DateBorder.addView(date_display);
                mainLayout.addView(DateBorder);
            }

            LinearLayout TransactionBorder = new LinearLayout(this);
            TransactionBorder.setOrientation(LinearLayout.HORIZONTAL);
            TransactionBorder.setLayoutParams(layoutParams);
            TransactionBorder.setBackgroundResource(R.drawable.border_set_top);

            TextView name_display = new TextView(this);
            name_display.setPaddingRelative(8,8,8,8);
            name_display.setPadding(8,8,8,8);
            name_display.setText(transactions.get(i).getName());
            name_display.setLayoutParams(textParams);

            //Need another LinearLayout to justify the stuff
            LinearLayout buttonsCost = new LinearLayout(this);
            buttonsCost.setOrientation(LinearLayout.HORIZONTAL);
            buttonsCost.setGravity(Gravity.RIGHT);

            TextView cost_display = new TextView(this);
            cost_display.setPaddingRelative(8,8,8,8);
            cost_display.setPadding(8,8,15,8);
            /*Formats the fucking string && adds extra 0 if need to*/
            String item_cost = "$" + Double.toString(transactions.get(i).getCost());
            int decimalIdx = item_cost.indexOf('.');
            if (decimalIdx + 1 == item_cost.length() - 1) {
                item_cost = item_cost + "0";
            }
            cost_display.setText(item_cost);
            cost_display.setLayoutParams(noWeight);

            Button plus = new Button(this);
            plus.setLayoutParams(btnParams);
            plus.setText("+");
            plus.setPadding(0,0,0,0);

            final TextView quantity = new TextView(this);
            quantity.setText("0");
            quantity.setLayoutParams(btnParams);
            quantity.setGravity(Gravity.CENTER);
            quantity.setId(i);

            Button minus = new Button(this);
            minus.setLayoutParams(btnParams);
            minus.setText("-");
            minus.setPadding(0,0,0,0);

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantity.setText("" + ((int) Integer.parseInt((String) quantity.getText()) + 1));
                    numberOfPurchase[quantity.getId()]++;
                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (quantity.getText().equals("0"))
                        return;
                    quantity.setText("" + ((int) Integer.parseInt((String) quantity.getText()) - 1));
                    numberOfPurchase[quantity.getId()]--;
                }
            });


            //Adds the two displays to the transaction line
            TransactionBorder.addView(name_display);
            buttonsCost.addView(minus);
            buttonsCost.addView(quantity);
            buttonsCost.addView(plus);
            buttonsCost.addView(cost_display);
            TransactionBorder.addView(buttonsCost);


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
                return false;
            case R.id.nav_help:
                return false;

            default:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
        }
    }
}