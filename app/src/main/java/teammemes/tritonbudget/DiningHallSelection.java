package teammemes.tritonbudget;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;

/* Author: Andy Lum */

public class DiningHallSelection extends AppCompatActivity {

    //Subtext Size (colleges)
    final float SUBSIZE = (float) .85;

    //Restaurant Name Lenghts
    final int SIXFOUR = 11;
    final int CAFEV = 13;
    final int CV = 12;
    final int FOODWORX = 8;
    final int OVT = 9;
    final int PINES = 5;

    final int BISTRO = 10;
    final int CLUBMED = 8;
    final int FLAVOR = 20;
    final int GOODYS = 7;
    final int GOODYS2 = 20;
    final int ROOTS = 6;
    final int SIXFOURNO = 9;

    public DiningHallSelection() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_hall_selection);

        //This is meant for the college locations ie subtext
        RelativeSizeSpan relsubsize = new RelativeSizeSpan(SUBSIZE);
        ForegroundColorSpan relcolor = new ForegroundColorSpan(Color.GRAY);

        /* Get IDs */
        //Dining Halls
        TextView sixtyfour = (TextView) findViewById(R.id.DH_TextView_64);
        TextView cafev = (TextView) findViewById(R.id.DH_TextView_CafeV);
        TextView canyonv = (TextView) findViewById(R.id.DH_TextView_CanyonV);
        TextView foodworx = (TextView) findViewById(R.id.DH_TextView_FoodWorx);
        TextView ovt = (TextView) findViewById(R.id.DH_TextView_OVT);
        TextView pines = (TextView) findViewById(R.id.DH_TextView_Pines);

        //FT & Specialities
        TextView bistro = (TextView) findViewById(R.id.DH_TextView_Bistro);
        TextView clubmed = (TextView) findViewById(R.id.DH_TextView_ClubMed);
        TextView flavorstruck = (TextView) findViewById(R.id.DH_TextView_FlavorFT);
        TextView goodys = (TextView) findViewById(R.id.DH_TextView_Goodys);
        TextView goodysFT = (TextView) findViewById(R.id.DH_TextView_GoodysFT);
        TextView roots = (TextView) findViewById(R.id.DH_TextView_Roots);
        TextView sixtyfournorth = (TextView) findViewById(R.id.DH_TextView_64North);

        TextView[] dtv = {sixtyfour, cafev, canyonv, foodworx, ovt, pines, bistro, clubmed,
                flavorstruck, goodys, goodysFT, roots, sixtyfournorth};
        String[] diningHalls = getResources().getStringArray(R.array.Dining_Halls_Array);
        int[] dnamelenghts = {SIXFOUR, CAFEV, CV, FOODWORX, OVT, PINES, BISTRO, CLUBMED, FLAVOR, GOODYS,
                GOODYS2, ROOTS, SIXFOURNO};

        /*Set Text for TextView Objects*/
        String text;
        SpannableString format;
        for (int i = 0; i < 13; i++) {
            text = diningHalls[i];
            format = new SpannableString(text);
            format.setSpan(relsubsize, dnamelenghts[i], text.length(), 0);
            format.setSpan(relcolor, dnamelenghts[i], text.length(), 0);
            dtv[i].setText(format);
        }

        /*Listeners*/
        //This can be optimized with a for-loop. It is currently left like this
        //for back-end to tinker with
        //Dining Halls
        sixtyfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Attempt to open another activity (Dining Hall Menu)
                Intent intent = new Intent(DiningHallSelection.this, testClick.class);
                startActivity(intent);
            }
        });
        cafev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Attempt to open another activity (Dining Hall Menu)
                Intent intent = new Intent(DiningHallSelection.this, testClick.class);
                startActivity(intent);
            }
        });
        canyonv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Attempt to open another activity (Dining Hall Menu)
                Intent intent = new Intent(DiningHallSelection.this, testClick.class);
                startActivity(intent);
            }
        });
        foodworx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Attempt to open another activity (Dining Hall Menu)
                Intent intent = new Intent(DiningHallSelection.this, testClick.class);
                startActivity(intent);
            }
        });
        ovt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Attempt to open another activity (Dining Hall Menu)
                Intent intent = new Intent(DiningHallSelection.this, testClick.class);
                startActivity(intent);
            }
        });
        pines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Attempt to open another activity (Dining Hall Menu)
                Intent intent = new Intent(DiningHallSelection.this, testClick.class);
                startActivity(intent);
            }
        });

        //FT and Specialities
        bistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Attempt to open another activity (Dining Hall Menu)
                Intent intent = new Intent(DiningHallSelection.this, testClick.class);
                startActivity(intent);
            }
        });
        clubmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Attempt to open another activity (Dining Hall Menu)
                Intent intent = new Intent(DiningHallSelection.this, testClick.class);
                startActivity(intent);
            }
        });
        flavorstruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Attempt to open another activity (Dining Hall Menu)
                Intent intent = new Intent(DiningHallSelection.this, testClick.class);
                startActivity(intent);
            }
        });
        goodys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Attempt to open another activity (Dining Hall Menu)
                Intent intent = new Intent(DiningHallSelection.this, testClick.class);
                startActivity(intent);
            }
        });
        goodysFT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Attempt to open another activity (Dining Hall Menu)
                Intent intent = new Intent(DiningHallSelection.this, testClick.class);
                startActivity(intent);
            }
        });
        roots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Attempt to open another activity (Dining Hall Menu)
                Intent intent = new Intent(DiningHallSelection.this, testClick.class);
                startActivity(intent);
            }
        });
        sixtyfournorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Attempt to open another activity (Dining Hall Menu)
                Intent intent = new Intent(DiningHallSelection.this, testClick.class);
                startActivity(intent);
            }
        });


    }//End of onCreate
}
