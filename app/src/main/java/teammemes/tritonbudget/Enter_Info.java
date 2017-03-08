package teammemes.tritonbudget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Enter_Info extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    // backend
    //Database mydb;
    //MenuDataSource menuDS;
    // frontend
    private EditText name;
    private EditText money;
    //private EditText id;
    private Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter__info);
        // Frontend inti.
        name = (EditText) findViewById(R.id.EnterName);
        money = (EditText) findViewById(R.id.EnterMoney);
        btnDone = (Button) findViewById(R.id.btnConfirm);
        sharedpreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);

        // add data button
        addData();

    }

   public void addData() {
        btnDone.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        User you=User.getInstance(getApplicationContext());
                        String usename = name.getText().toString();
                        String usemoney = money.getText().toString();
                        if (usename.length() == 0) {
                            Toast.makeText(Enter_Info.this, "Please enter user name", Toast.LENGTH_LONG).show();
                        } else if (usemoney.length() == 0) {
                            Toast.makeText(Enter_Info.this, "Please enter Balance", Toast.LENGTH_LONG).show();
                        } else {
                            you.setBalance(Double.parseDouble(usemoney));
                            you.setName(usename);
                            Toast.makeText(Enter_Info.this, "Thanks", Toast.LENGTH_LONG).show();
                            Intent home = new Intent(getApplicationContext(), HomeScreen.class);
                            startActivity(home);
                        }
                    }

                }
        );
    }


}
