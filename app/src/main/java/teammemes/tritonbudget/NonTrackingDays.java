package teammemes.tritonbudget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NonTrackingDays extends AppCompatActivity {
    ArrayList<String> noneatingdays = new ArrayList<String>();
    private CalendarView calendar;
    private TextView daysHeld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nontracking);
        calendar = (CalendarView) findViewById(R.id.calendarView2);
        calendar.setShowWeekNumber(false);
        calendar.setFirstDayOfWeek(1);
        daysHeld = (TextView) findViewById(R.id.NonTracking_TV);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(), "" + dayOfMonth, Toast.LENGTH_SHORT).show();// TODO Auto-generated method stub
                String noeat = "" + month + "/" + dayOfMonth + "/" + year + "  ";
                noneatingdays.add(noeat);
                int noneatingamount = noneatingdays.size();
                daysHeld.append(noneatingdays.get(noneatingamount - 1));
            }
        });
    }
}
