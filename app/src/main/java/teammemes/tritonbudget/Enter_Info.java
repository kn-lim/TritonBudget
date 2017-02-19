package teammemes.tritonbudget;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Enter_Info extends AppCompatActivity {
    // frontend
    private EditText name;
    private EditText money;
    private TextView day;
    private Button btnDone;
    private Button btnView;
    // backend
    Database mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter__info);
        // Backend Inti.
        mydb = new Database(this);

        // Frontend inti.
        name = (EditText) findViewById(R.id.EnterName);
        money = (EditText) findViewById(R.id.EnterMoney);
        day = (TextView) findViewById(R.id.day);
        btnDone = (Button)findViewById(R.id.confirm);
        btnView = (Button)findViewById(R.id.btnViewAll);

        // add data button
        AddData();
        // view all button
        ViewAll();
    }

    public void AddData(){
        btnDone.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        // BackEnd
                        boolean isInserted = mydb.insertData(name.getText().toString(), money.getText().toString());
                        if (isInserted == true)
                            Toast.makeText(Enter_Info.this,"DATA inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Enter_Info.this,"DATA not inserted", Toast.LENGTH_LONG).show();

                        // frontend
                        double CurrMoney = 0;
                        // get remaining days in 2016-2017 school year
                        Calendar cali = Calendar.getInstance();
                        int currDay = cali.get(Calendar.DAY_OF_YEAR);
                        int remain = 167 - currDay;

                        //
                        if (!money.getText().toString().equals("")) {
                            CurrMoney = Double.parseDouble(money.getText().toString());
                        }
                        double averageSpending = CurrMoney / (double)remain;
                        day.setText(String.format("$ %.2f", averageSpending));
                    }
                }
        );
    }

    public void ViewAll(){
        btnView.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Cursor res = mydb.getAllData();
                        if(res.getCount() == 0){
                            showMessage("NO DATA", "NO data Found" );
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("ID :"+ res.getString(0) + "\n");
                            buffer.append("Name :"+ res.getString(1) + "\n");
                            buffer.append("Money :"+ res.getString(2) + "\n");

                        }
                        // show message
                        showMessage("DATA", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
