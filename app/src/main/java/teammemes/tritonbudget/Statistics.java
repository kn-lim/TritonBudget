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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import teammemes.tritonbudget.db.HistoryDataSource;

public class Statistics extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Spinner spinner;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private User usr;


    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_statistics);



        usr = User.getInstance(getApplicationContext());

        //NAVIGATION DRAWER//
        //Creates the toolbar to the one defined in nav_action
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Statistics");

        //Create the Drawer layout and the toggle
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_statistics_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        //Create the navigationView and add a listener to listen for menu selections
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView= navigationView.getHeaderView(0);
        TextView usrName = (TextView) navHeaderView.findViewById(R.id.header_name);
        usrName.setText(usr.getName());

        spinner = (Spinner) findViewById(R.id.spinner);
        // add data button
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    week();
                } else {
                    month();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        TextView weekTotal = (TextView)findViewById(R.id.statistics_text_this_week_num);
        TextView average = (TextView)findViewById(R.id.statistic_text_average_num);
        HistoryDataSource hist = new HistoryDataSource(getApplicationContext());

        double weektotal=hist.getThisWeekTotal();
        weektotal=weektotal*100;
        weektotal=Math.round(weektotal);
        weektotal=weektotal/100;
        weekTotal.setText(Double.toString(weektotal));
        double daily = 100*hist.getDailyAverage();
        daily=Math.round(daily);
        daily=daily/100;
        average.setText(Double.toString(daily));

        //For Last week, this week,
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
        Spinner spinner;
        //Reacts to the item selected depending on which was pressed
        //Creates a new Intent for the new page and starts that activity
        switch (id) {
            case R.id.nav_home:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, HomeScreen.class);
                nextScreen.putExtra("FROM", "Statistics");
                startActivity(nextScreen);
                return true;
            case R.id.nav_history:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, History.class);
                nextScreen.putExtra("FROM", "Statistics");
                startActivity(nextScreen);
                return true;
            case R.id.nav_statistics:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            case R.id.nav_menus:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, DiningHallSelection.class);
                nextScreen.putExtra("FROM", "Statistics");
                startActivity(nextScreen);
                return true;
            case R.id.nav_settings:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, Settings.class);
                nextScreen.putExtra("FROM", "Statistics");
                startActivity(nextScreen);
                return true;
            case R.id.nav_help:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, Help.class);
                nextScreen.putExtra("FROM", "Statistics");
                startActivity(nextScreen);
                return true;
            default:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
        }

    }

    private void week() {
        BarChart chart = (BarChart) findViewById(R.id.chart);

        BarData data = new BarData(getXAxisValues(), getDataWeek());
        chart.setData(data);
        chart.setDescription("My Chart");
        chart.animateXY(2000, 2000);
        chart.invalidate();
    }

    private void month() {
        BarChart chart = (BarChart) findViewById(R.id.chart);

        BarData data = new BarData(getXAxisValuestwo(), getDataMonth());
        chart.setData(data);
        chart.setDescription("My Chart");
        chart.animateXY(2000, 2000);
        chart.invalidate();
    }

    private ArrayList<BarDataSet> getDataWeek() {
        HistoryDataSource hist = new HistoryDataSource(getApplicationContext());
        double data[] = hist.getTransactionByWeek();
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            BarEntry vl = new BarEntry((float) data[i], i);
            valueSet1.add(vl);
        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(Color.rgb(0, 0, 155));


        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        return dataSets;
    }

    private ArrayList<BarDataSet> getDataMonth() {
        HistoryDataSource hist = new HistoryDataSource(getApplicationContext());
        double data[] = hist.getTransactionByMonth();
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            BarEntry vl = new BarEntry((float) data[i], i);
            valueSet1.add(vl);
        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(Color.rgb(0, 0, 155));


        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime((new Date()));
        for(int i=0;i<7;i++)
        {
            cal.setTime(new Date(System.currentTimeMillis()-24L*60L*60L*1000L*i/2));
            //cal.add(Calendar.DATE, -1);
            int month = cal.get(Calendar.MONTH)+1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String date = month + "." + day;
            xAxis.add(date);
        }
        return xAxis;
    }

    private ArrayList<String> getXAxisValuestwo() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Week1");
        xAxis.add("Week2");
        xAxis.add("Week3");
        xAxis.add("Week4");

        return xAxis;
    }
}
