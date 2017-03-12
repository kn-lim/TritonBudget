package teammemes.tritonbudget;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class NonTrackingDays extends Activity {
    ArrayList<String> noneatingdays = new ArrayList<>();
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int inityear, initmonth, initday;
    private User usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nontracking);

        usr = User.getInstance(getApplicationContext());

        //dateView = (TextView) findViewById(R.id.NED_TV_LIST);
        calendar = Calendar.getInstance();
        inityear = calendar.get(Calendar.YEAR);
        initmonth = calendar.get(Calendar.MONTH);
        initday = calendar.get(Calendar.DAY_OF_MONTH);
        //showDate(year, month+1, day);

        TextView test = (TextView) findViewById(R.id.NED_TV_LIST);
        noneatingdays = usr.getNon_tracking_days();
        Collections.sort(noneatingdays);
        for (int i = 0; i < noneatingdays.size(); i++) {
            test.append(" " + noneatingdays.get(i));
        }

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
                    } else{
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
        //dateView.append(str);
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
            showDate(curryear, monthOfYear + 1, dayOfMonth);
        } else {
            Toast.makeText(getApplicationContext(), "Already accounted for!", Toast.LENGTH_SHORT).show();
        }
    }
}