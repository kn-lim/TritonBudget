package teammemes.tritonbudget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DiningHallSelection extends AppCompatActivity {

    //Dining Hall Text and FT Text
    private TextView dininghalltext; //textview for string "Dining Hall"
    private TextView ftandspectext; //textview for string "FT & Speciality Rest."

    private LinearLayout sixtyfour;
    private LinearLayout cafev;
    private LinearLayout canyonv;
    private LinearLayout foodworx;
    private LinearLayout ovt;
    private LinearLayout pines;

    private LinearLayout bistro;
    private LinearLayout clubmed;
    private LinearLayout flavorstruck;
    private LinearLayout goodys;
    private LinearLayout roots;
    private LinearLayout sixtyfournorth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_hall_selection);

        /* Get IDs */
        //Dining Halls
        sixtyfour = (LinearLayout) findViewById(R.id.DH_LLHClickable_64);
        cafev = (LinearLayout) findViewById(R.id.DH_LLHClickable_CafeV);
        canyonv = (LinearLayout) findViewById(R.id.DH_LLHClickable_CanyonV);
        foodworx = (LinearLayout) findViewById(R.id.DH_LLHClickable_Foodworx);
        ovt = (LinearLayout) findViewById(R.id.DH_LLHClickable_OVT);
        pines = (LinearLayout) findViewById(R.id.DH_LLHClickable_Pines);
        //Food trucks and Speciality Restaurants
        bistro = (LinearLayout) findViewById(R.id.DH_LLHClickable_Bistro);
        clubmed = (LinearLayout) findViewById(R.id.DH_LLHClickable_ClubMed);
        flavorstruck = (LinearLayout) findViewById(R.id.DH_LLHClickable_FlavorFT);
        goodys = (LinearLayout) findViewById(R.id.DH_LLHClickable_Goodys);
        roots = (LinearLayout) findViewById(R.id.DH_LLHClickable_Roots);
        sixtyfournorth = (LinearLayout) findViewById(R.id.DH_LLHClickable_64North);

        /* Set Listeners */
        sixtyfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Attempt to open another activity (Dining Hall Menu)
                Intent intent = new Intent(DiningHallSelection.this, testClick.class);
                startActivity(intent);
            }
        });
    }
}
