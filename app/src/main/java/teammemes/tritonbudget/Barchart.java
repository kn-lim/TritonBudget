package teammemes.tritonbudget;
import android.graphics.Color;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.view.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

public class Barchart extends AppCompatActivity {
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barchart);
        // Frontend inti.

        spinner = (Spinner) findViewById(R.id.spinner);
        // add data button
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
a();
                }
                else
                {
b();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void a()
    {
        BarChart chart = (BarChart) findViewById(R.id.chart);

        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("My Chart");
        chart.animateXY(2000, 2000);
        chart.invalidate();
    }
    private void b()
    {
        BarChart chart = (BarChart) findViewById(R.id.chart);

        BarData data = new BarData(getXAxisValuestwo(), getDataSettwo());
        chart.setData(data);
        chart.setDescription("My Chart");
        chart.animateXY(2000, 2000);
        chart.invalidate();
    }
    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(110.000f, 0);
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(40.000f, 1);
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(60.000f, 2);
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(30.000f, 3);
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(90.000f, 4);
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(100.000f, 5);
        valueSet1.add(v1e6);
        BarEntry v1e7 = new BarEntry(100.000f, 6);
        valueSet1.add(v1e7);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(Color.rgb(0, 155, 0));


        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        return dataSets;
    }
    private ArrayList<BarDataSet> getDataSettwo() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(110.00f, 0);
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(20.000f, 1);
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(70.000f, 2);
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(50.000f, 3);
        valueSet1.add(v1e4);


        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(Color.rgb(0, 155, 0));


        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        return dataSets;
    }
    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Sun");
        xAxis.add("Mon");
        xAxis.add("Tue");
        xAxis.add("Wed");
        xAxis.add("Thu");
        xAxis.add("Fri");
        xAxis.add("Sat");
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
