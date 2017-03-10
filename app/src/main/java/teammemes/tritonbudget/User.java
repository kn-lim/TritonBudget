package teammemes.tritonbudget;
//package com.example.andrewli.inputinformation;

import android.content.Context;
import android.content.SharedPreferences;
import java.math.RoundingMode;
/**
 *
 */

public class User {
    private String Name;
    private double Balance;
    private static User user=null;
    SharedPreferences prefs ;
    private User(Context context) {
        prefs= context.getSharedPreferences("user", Context.MODE_PRIVATE);
        Name = prefs.getString("name", "user name not found");
        Balance = Double.parseDouble(prefs.getString("balance","0.0"));
    }


    public static User getInstance(Context context)
    {
        if(user==null)
        {
            user=new User(context);
        }
        return user;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", name);
        editor.commit();
        Name = prefs.getString("name", "fail to set name");
    }

    public double getBalance() {

        Balance=Balance*100;
        Balance=Math.round(Balance);
        Balance=Balance/100;
        return Balance;
    }

    public void setBalance(double Balance) {
        Balance=Balance*100;
        Balance=Math.round(Balance);
        Balance=Balance/100;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("balance", Double.toString(Balance));
        editor.commit();
        this.Balance = Double.parseDouble(prefs.getString("balance", "fail to set balance"));
    }

}
