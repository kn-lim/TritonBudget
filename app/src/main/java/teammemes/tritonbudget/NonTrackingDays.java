package teammemes.tritonbudget;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import teammemes.tritonbudget.db.TranHistory;

import static android.R.attr.id;
import static android.view.Gravity.CENTER;
import static android.view.Gravity.RIGHT;

public class NonTrackingDays extends Activity {
    ArrayList<String> noneatingdays = new ArrayList<>();
    private DatePicker datePicker;
    private Calendar calendar;
    private int inityear, initmonth, initday;
    private User usr;
    int id = 100;
    LinearLayout.LayoutParams layoutParams, textParams, btnParams, costParams;
    LinearLayout mainLayout;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nontracking);

        usr = User.getInstance(getApplicationContext());

        calendar = Calendar.getInstance();
        inityear = calendar.get(Calendar.YEAR);
        initmonth = calendar.get(Calendar.MONTH);
        initday = calendar.get(Calendar.DAY_OF_MONTH);

        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        costParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        btnParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);  //was (200,100)
        btnParams.setMargins(0, 0, 0, 0); //was (0,0,0,40)

        /*
        TextView test = (TextView) findViewById(R.id.NED_TV_LIST);
        noneatingdays = usr.getNon_tracking_days();
        Collections.sort(noneatingdays);
        for (int i = 0; i < noneatingdays.size(); i++) {
            test.append(" " + noneatingdays.get(i));
        }
        */
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
            //showDate(curryear, monthOfYear + 1, dayOfMonth);
            //Collections.sort(noneatingdays);
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
            date_border.setBackgroundResource(R.drawable.border_set_top);
            date_border.setOrientation(LinearLayout.HORIZONTAL);
            date_border.setLayoutParams(layoutParams);

            //Creates the date_display textview and adds it to "date_border"
            final TextView date_display = new TextView(this);
            date_display.setGravity(CENTER);
            date_display.setPaddingRelative(8, 8, 8, 8);
            date_display.setPadding(8, 8, 8, 8);
            date_display.setText(str_to_display);
            date_display.setTextSize(26);
            date_display.setLayoutParams(textParams);

            //Add the textview to the date_border
            date_border.addView(date_display);
            //add the date_border to the "main_layout"
            mainLayout.addView(date_border);


            //Creates the onLongClickListener for the transaction:
            //      If the transaction is held an edit button appears
            final LinearLayout edit_container = new LinearLayout(this);
            edit_container.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            edit_container.setWeightSum(1);

            date_border.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //If the edit button is already visible do nothing
                    if (date_border.getChildCount() >= 2) {
                        System.out.println("hello");
                        System.out.print(date_border.getChildCount()>=3);
                        return false;
                    }
                    removeAllButtons();

                    //Add another LLH to push the edit button to the right
                    edit_container.setGravity(RIGHT);
                    date_border.addView(edit_container);
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

                            System.out.println(toRemove);
                            NED.remove(toRemove);
                            usr.remove_days(toRemove);

                            ArrayList<String> potato = usr.getNon_tracking_days();
                            System.out.println(potato.contains(toRemove));
                            render_non_eating(NED);
                        }
                    });
                    if(edit_container.getChildCount()<1) {
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
    }

    public void removeAllButtons(){
        int numTrans = mainLayout.getChildCount();
        for (int j = 0; j < numTrans; j++){
            LinearLayout tran = (LinearLayout) mainLayout.getChildAt(j);
            if (tran.getChildCount() == 2){
                tran.removeViewAt(1);
            }
        }
    }
}