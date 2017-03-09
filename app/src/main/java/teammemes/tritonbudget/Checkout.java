package teammemes.tritonbudget;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import java.util.ArrayList;
import teammemes.tritonbudget.db.TranHistory;
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
        Bundle extras = this.getIntent().getExtras();
        ArrayList<TranHistory> trans = (ArrayList<TranHistory>) extras.getSerializable("Transactions");
        Checkout_Adapter co_adapter = new Checkout_Adapter(this, trans);
        ListView CO_View = (ListView) findViewById(R.id.ItemsContainer);
        CO_View.setAdapter(co_adapter);
    }
}
