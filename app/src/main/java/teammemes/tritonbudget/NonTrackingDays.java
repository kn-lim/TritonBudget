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
    private int year, month, day;
    private User usr;
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day


                    String date_noeat = arg1 + "/" + (arg2 + 1) + "/" + arg3;

                    noneatingdays = usr.getNon_tracking_days();

                    if (!noneatingdays.contains(date_noeat)) {
                        usr.setNon_tracking_days(date_noeat);
                        showDate(arg1, arg2 + 1, arg3);
                    }
                }

            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nontracking);

        usr = User.getInstance(getApplicationContext());


        dateView = (TextView) findViewById(R.id.textView3);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        //showDate(year, month+1, day);

        TextView test = (TextView) findViewById(R.id.textView5);
        noneatingdays = usr.getNon_tracking_days();
        Collections.sort(noneatingdays);
        for (int i = 0; i < noneatingdays.size() - 1; i++) {
            test.append(noneatingdays.get(i) + " ");
        }

    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private void showDate(int year, int month, int day) {
        dateView.append(new StringBuilder().append(month).append("/")
                .append(day).append("/").append(year));
    }
}