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
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import teammemes.tritonbudget.db.HistoryDataSource;
import teammemes.tritonbudget.db.TranHistory;

public class Settings extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    final Context context = this;
    //Nav Drawers
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private User usr;
    private HistoryDataSource database;
    private TextView usrName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_settings);
        usr = User.getInstance(getApplicationContext());

        database = new HistoryDataSource(this);

        //Creates the toolbar to the one defined in nav_action
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");

        //Create the Drawer layout and the toggle
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_settings);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        //Create the navigationView and add a listener to listen for menu selections
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView= navigationView.getHeaderView(0);
        usrName = (TextView) navHeaderView.findViewById(R.id.header_name);
        usrName.setText(usr.getName());

        //Settings Options
        TextView changeName = (TextView) findViewById(R.id.settings_changeName);
        TextView changeBalance = (TextView) findViewById(R.id.settings_changeBalance);
        TextView addDD = (TextView) findViewById(R.id.settings_addDD);
        TextView test_load = (TextView) findViewById(R.id.settings_test_load);
        TextView credits = (TextView) findViewById(R.id.settings_credits);

        //Listeners
        changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("What's Your Name?");

                LayoutInflater viewInflated = LayoutInflater.from(context);
                View deductView = viewInflated.inflate(R.layout.dialog_change_name,null);

                // Set up the input

                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                builder.setView(deductView);

                final EditText input = (EditText) deductView.findViewById(R.id.changeName_input);

                // Set up the buttons
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value= input.getText().toString();
                        if (value.length() == 0 || value.length() > 25) {
                            Toast.makeText(Settings.this, "Please up to 25 characters.\nName not saved.", Toast.LENGTH_LONG).show();
                        } else {
                            usr.setName(value);
                            usrName.setText(usr.getName());
                            Toast.makeText(Settings.this, "Changed Name to " + value, Toast.LENGTH_LONG).show();
                        }
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

        changeBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("What's Your Balance?");

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
                            s.delete(posDot + 3,  posDot + 4);
                        }
                    }
                });

                // Set up the buttons
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double value = Double.parseDouble(input.getText().toString());
                        if (value > 9999.99 || value < 0) {
                            Toast.makeText(Settings.this, "Please enter an amount $[0, 9999.99].\nBalance was not changed.", Toast.LENGTH_LONG).show();
                        } else {
                            usr.setBalance(value);
                            Toast.makeText(Settings.this, "Changed Balance to $" + value, Toast.LENGTH_LONG).show();
                        }
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

        addDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("How Much Dining Dollars?");

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
                            s.delete(posDot + 3,  posDot + 4);
                        }
                    }
                });

                // Set up the buttons
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double value = Double.parseDouble(input.getText().toString());
                        usr.setBalance(usr.getBalance() + value);

                        TranHistory transaction = new TranHistory(1,"Added Dining Dollars",1,new Date(), 0 - value);
                        database.createTransaction(transaction);

                        Toast.makeText(Settings.this, "Added $" + value + " Dining Dollars", Toast.LENGTH_LONG).show();
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

        test_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("What's Your Name?");

                LayoutInflater viewInflated = LayoutInflater.from(context);
                View deductView = viewInflated.inflate(R.layout.dialog_change_name,null); //TODO: CHANGE THIS

                // Set up the input

                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                builder.setView(deductView);

                final EditText input = (EditText) deductView.findViewById(R.id.changeName_input); //TODO: CHANGE THIS

                // Set up the buttons
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: CHANGE THIS
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

        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Credits");

                LayoutInflater viewInflated = LayoutInflater.from(context);
                View deductView = viewInflated.inflate(R.layout.dialog_credits,null);

                // Set up the input

                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                builder.setView(deductView);

                builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
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
}
