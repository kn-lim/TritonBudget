package teammemes.tritonbudget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

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

        Checkout_Adapter co_adapter = new Checkout_Adapter(this, Checkout_Item.getFoodItems());
        ListView CO_View = (ListView) findViewById(R.id.ItemsContainer);

        CO_View.setAdapter(co_adapter);

    }
}
