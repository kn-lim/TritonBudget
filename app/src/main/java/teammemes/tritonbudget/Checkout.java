package teammemes.tritonbudget;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Date;

import teammemes.tritonbudget.db.*;

import android.content.Intent;
import android.widget.TextView;

public class Checkout extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Uses custom adapter to populate list view
        populateCOList();
    }
    private void populateCOList() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.Checkout);

        Intent it=getIntent();
        ArrayList<String> transtring =  it.getStringArrayListExtra("Transactions");
        ArrayList<TranHistory> trans = new ArrayList<>();
        ArrayList<String> num = it.getStringArrayListExtra("number");
        MenuDataSource data = new MenuDataSource(getApplicationContext());
        double total = 0;
        for(int i=0;i<transtring.size();i++)
        {
            Menu men = data.getMenuById(Integer.parseInt(transtring.get(i)));
            trans.add(new TranHistory(men.getId(),men.getName(),Integer.parseInt(num.get(i)),new Date(),men.getCost()));
            total += (trans.get(i).getCost() * trans.get(i).getQuantity());
            TextView t = makeTV(trans.get(i).getName(), String.valueOf(trans.get(i).getCost()));
            ll.addView(t);
        }

        change_balance(total);

        Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
        //startActivity(intent);
    }

    private TextView makeTV(String name, String cost) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setText(name + " " + cost);
        tv.setTextSize(20);
        return tv;
    }

    // add button then call this in listener
        private void change_balance(double bal) {
            User usr = User.getInstance(getApplicationContext());
            usr.setBalance(usr.getBalance() - bal);
        }
}
