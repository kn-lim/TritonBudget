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
import android.provider.Settings.Secure;

import java.io.InputStream;
import java.util.Calendar;
import java.util.List;


public class Enter_Info extends AppCompatActivity {
    // frontend
    private EditText name;
    private EditText money;
    private EditText id;
    private TextView day;
    private Button btnDone;
    private Button btnView;
    private Button btnDelete;
    private Button btnUpdate;
    private Button btnBuy;
    // backend
    Database mydb;
    MenuDataSource menuDS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter__info);
        // Backend Inti.
        menuDS = new MenuDataSource(this);
        mydb = new Database(this);
        // InputStream inputStream = getResources().openRawResource(R.raw.menu);


        //mydb.populateMenu(inputStream);

        // Frontend inti.
        name = (EditText) findViewById(R.id.EnterName);
        money = (EditText) findViewById(R.id.EnterMoney);
        day = (TextView) findViewById(R.id.day);
        id = (EditText) findViewById(R.id.EnterID);
        btnDone = (Button) findViewById(R.id.btnConfirm);
        btnView = (Button) findViewById(R.id.btnViewAll);
        btnDelete = (Button) findViewById(R.id.delete);
        btnUpdate = (Button) findViewById(R.id.update);
        btnBuy = (Button) findViewById(R.id.buy);

        // add data button
        addData();
        // view all button
        viewAll();

        // update Data
        updateData();
        // delete data
        deleteData();
        // buy
        buy();
    }


    public void addData() {
        btnDone.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // BackEnd
                        boolean isInserted = mydb.insertData(name.getText().toString(), money.getText().toString());
                        if (isInserted == true)
                            Toast.makeText(Enter_Info.this, "DATA inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Enter_Info.this, "DATA not inserted", Toast.LENGTH_LONG).show();

                        // frontend
//                        Intent i = new Intent(getApplicationContext(),budget_screen.class);
//                        startActivity(i);
//                        setContentView(R.layout.activity_budget_screen);

                        double CurrMoney = 0;
                        // get remaining days in 2016-2017 school year
                        Calendar cali = Calendar.getInstance();
                        int currDay = cali.get(Calendar.DAY_OF_YEAR);
                        int remain = 167 - currDay;

                        //
                        if (!money.getText().toString().equals("")) {
                            CurrMoney = Double.parseDouble(money.getText().toString());
                        }
                        double averageSpending = CurrMoney / (double) remain;
                        day.setText(String.format("$ %.2f", averageSpending));
                    }
                }
        );
    }

    public void viewAll() {
        btnView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuDS.open();
                        //List<Menu> menuList = menuDS.getAllMenus();
                        List<Menu> menuList = menuDS.getMenusByLocation("Pines");
                        menuDS.close();
                        showMessage("DATA", menuList.toString());
                    }
                }
        );
    }

    public void deleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = mydb.deleteData(id.getText().toString());
                        if (deletedRows > 0)
                            Toast.makeText(Enter_Info.this, "Data Deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Enter_Info.this, "Data not Deleted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void updateData() {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = mydb.updateData(id.getText().toString(), name.getText().toString(), money.getText().toString());
                        if (isUpdate == true)
                            Toast.makeText(Enter_Info.this, "Data Update", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Enter_Info.this, "Data not Updated", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void buy() {
        btnBuy.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isBought = mydb.updateData(id.getText().toString(), name.getText().toString(), money.getText().toString());
                        if (isBought == true)
                            Toast.makeText(Enter_Info.this, "Data bought", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Enter_Info.this, "Data not bought", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
