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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import teammemes.tritonbudget.Menus.Menu;
import teammemes.tritonbudget.db.HistoryDataSource;
import teammemes.tritonbudget.db.MenuDataSource;
import teammemes.tritonbudget.db.TranHistory;

public class Checkout extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayout mainLayout;
    Toolbar mToolbar;
    ActionBarDrawerToggle mToggle;
    double total = 0;
    private DrawerLayout mDrawerLayout;
    private ArrayList<TranHistory> trans;
    private float dX;
    private float dY;
    private int lastAction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        User usr = User.getInstance(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_checkout);

        //Creates the toolbar to the one defined in nav_action
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Checkout");


        //Create the Drawer layout and the toggle
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_checkout_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        //Create the navigationView and add a listener to listen for menu selections
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Get the navigation drawer header and set's the name to user's name
        View navHeaderView = navigationView.getHeaderView(0);
        TextView usrName = (TextView) navHeaderView.findViewById(R.id.header_name);
        usrName.setText(usr.getName());

        //Fetches the main empty layout
        mainLayout = (LinearLayout) findViewById(R.id.page);
        //Uses custom adapter to populate list view
        populateCOList();

        TextView display_total = (TextView)findViewById(R.id.total_cost);
        display_total.setText("Total:\t\t\t$" + double_to_string(total));
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.checkout_menu, menu);
        return true;
    }

    private void populateCOList() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.Checkout);
        ll.setBackgroundResource(R.drawable.border_set_top_bottom);

        Intent it = getIntent();
        ArrayList<String> transtring = it.getStringArrayListExtra("Transactions");
        trans = new ArrayList<>();
        ArrayList<String> num = it.getStringArrayListExtra("number");
        MenuDataSource data = new MenuDataSource(getApplicationContext());
        for (int i = 0; i < transtring.size(); i++) {
            Menu men = data.getMenuById(Integer.parseInt(transtring.get(i)));

            String menuName = men.getName();
            int numItems = Integer.parseInt(num.get(i));
            if (numItems > 1){
                menuName += " x"+numItems;
            }

            trans.add(new TranHistory(men.getId(), menuName, numItems, new Date(), men.getCost()*numItems));

            String cost = "$" + double_to_string(Math.round(trans.get(i).getCost()*100)/100);

            total += (trans.get(i).getCost());
            total *= 100;
            total = Math.round(total);
            total /= 100;

            LinearLayout borderll = makeLL();
            LinearLayout quantityll = makeLL();
            quantityll.setBackgroundResource(0);
            quantityll.setGravity(Gravity.RIGHT);

            TextView item = new TextView(this);
            item.setPadding(8, 8, 8, 8);
            item.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            item.setTextSize(20);
            String itemName = trans.get(i).getName();
            item.setText(itemName);

            TextView t = makeTV(cost); //, quantity);
            t.setPadding(8,8,8,8);

            ll.addView(borderll);
            borderll.addView(item);
            borderll.addView(quantityll);
            quantityll.addView(t);
        }

    }

    private LinearLayout makeLL() {
        LinearLayout nestedll = new LinearLayout(this);
        nestedll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        nestedll.setOrientation(LinearLayout.VERTICAL);
        nestedll.setBackgroundResource(R.drawable.border_set_top_bottom);
        return nestedll;
    }

    private TextView makeTV(String cost) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setText(cost);
        tv.setTextSize(20);
        return tv;
    }

    // add button then call this in listener
    private boolean change_balance(double bal) {
        User usr = User.getInstance(getApplicationContext());
        if (usr.getBalance() < bal) {
            return false;
        }
        usr.setBalance(usr.getBalance() - bal);
        HistoryDataSource data = new HistoryDataSource(getApplicationContext());
        for (int i = 0; i < trans.size(); i++) {
            data.createTransaction(trans.get(i));
        }
        return true;
    }

    private String double_to_string(double number) {
        //Gets the balance from the user
        String str = "" + number;
        int decimalIdx = str.indexOf('.');
        //Edge case, where balance == $XXX.00, it wrongly displays one instance of 0. This fixes it.
        if (decimalIdx + 1 == str.length() - 1) {
            str = str + "0";
        }
        return str;
    }

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
                nextScreen.putExtra("FROM", "Checkout");
                startActivity(nextScreen);
                return true;
            case R.id.nav_history:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            case R.id.nav_statistics:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, Statistics.class);
                nextScreen.putExtra("FROM", "Checkout");
                startActivity(nextScreen);
                return true;
            case R.id.nav_menus:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, DiningHallSelection.class);
                nextScreen.putExtra("FROM", "Checkout");
                startActivity(nextScreen);
                return true;
            case R.id.nav_settings:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, Settings.class);
                nextScreen.putExtra("FROM", "Checkout");
                startActivity(nextScreen);
                return true;
            case R.id.nav_help:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, Help.class);
                nextScreen.putExtra("FROM", "Checkout");
                startActivity(nextScreen);
                return true;

            default:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        if (id == R.id.confirmBtn){
            if (change_balance(total)) {
                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Purchased!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "You broke though.", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
