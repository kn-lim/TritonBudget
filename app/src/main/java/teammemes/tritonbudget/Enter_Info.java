package teammemes.tritonbudget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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

        money.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) {
                    return;
                }
                if (temp.length() - posDot - 1 > 2) {
                    s.delete(posDot + 3, posDot + 4);
                }
            }
        });
        btnDone = (Button) findViewById(R.id.btnConfirm);
        sharedpreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);

        //name.getBackground().setColorFilter(0xFFFFEB3B, PorterDuff.Mode.SRC_IN);
        //money.getBackground().setColorFilter(0xFFFFEB3B, PorterDuff.Mode.SRC_IN);

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
                        int decimalIdx = usemoney.indexOf('.');

                        String dollaramt;
                        if (decimalIdx != -1) {
                            dollaramt = usemoney.substring(0, decimalIdx);
                        } else {
                            dollaramt = usemoney;
                        }
                        if (usename.length() == 0 || usename.length() > 25) {
                            Toast.makeText(Enter_Info.this, "Please enter your name up to 25 characters", Toast.LENGTH_LONG).show();
                        } else if (usemoney.length() == 0) {
                            Toast.makeText(Enter_Info.this, "Please enter Balance", Toast.LENGTH_LONG).show();
                        } else if( dollaramt.length()>4) {
                            Toast.makeText(Enter_Info.this, "Please enter $ amounts in the format XXXX.XX.\nYou can't possibly have 5 figures in Dining Dollars", Toast.LENGTH_LONG).show();
                        }
                        else {
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
