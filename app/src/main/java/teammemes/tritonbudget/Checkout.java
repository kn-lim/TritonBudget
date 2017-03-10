package teammemes.tritonbudget;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Date;

import teammemes.tritonbudget.db.*;

import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

public class Checkout extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    LinearLayout mainLayout;
    double total = 0;
    private ArrayList<TranHistory> trans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toolbar mToolbar;
        DrawerLayout mDrawerLayout;
        ActionBarDrawerToggle mToggle;
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
        View navHeaderView= navigationView.getHeaderView(0);
        TextView usrName = (TextView) navHeaderView.findViewById(R.id.header_name);
        usrName.setText(usr.getName());

        //Fetches the main empty layout
        mainLayout = (LinearLayout) findViewById(R.id.page);
        //Uses custom adapter to populate list view
        populateCOList();

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.ConfirmPurchaseBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                    startActivity(intent);
                Toast.makeText(getApplicationContext(),"not enough balance!",Toast.LENGTH_LONG).show();

            }
        });
    }
    private void populateCOList() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.Checkout);

        Intent it=getIntent();
        ArrayList<String> transtring =  it.getStringArrayListExtra("Transactions");
        trans = new ArrayList<>();
        ArrayList<String> num = it.getStringArrayListExtra("number");
        MenuDataSource data = new MenuDataSource(getApplicationContext());
        for(int i=0;i<transtring.size();i++)
        {
            Menu men = data.getMenuById(Integer.parseInt(transtring.get(i)));
            trans.add(new TranHistory(men.getId(),men.getName(),Integer.parseInt(num.get(i)),new Date(),men.getCost()));
            String cost = Double.toString(trans.get(i).getCost());
            String quantity = Integer.toString(trans.get(i).getQuantity());
            total += (trans.get(i).getCost() * trans.get(i).getQuantity());
            TextView t = makeTV(trans.get(i).getName(), cost, quantity);
            ll.addView(t);
        }

    }

    private TextView makeTV(String name, String cost, String quantity) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setText(name + "\t\t\t" + cost + "\t\t\t" + quantity);
        tv.setTextSize(17);
        return tv;
    }

    // add button then call this in listener
    private boolean change_balance(double bal) {
        User usr = User.getInstance(getApplicationContext());
        if(usr.getBalance()<bal)
        {
            return false;
        }
        usr.setBalance(usr.getBalance() - bal);
        HistoryDataSource data=new HistoryDataSource(getApplicationContext());
        for(int i=0;i<trans.size();i++) {
            data.createTransaction(trans.get(i));
        }
        return true;
    }
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
