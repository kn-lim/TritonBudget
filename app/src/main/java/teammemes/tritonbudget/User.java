package teammemes.tritonbudget;
//package com.example.andrewli.inputinformation;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */

public class User {
    private static User user=null;
    SharedPreferences prefs ;
    private String Name;
    private double Balance;
    private ArrayList<String> non_tracking_days;
    private User(Context context) {
        prefs= context.getSharedPreferences("user", Context.MODE_PRIVATE);
        Name = prefs.getString("name", "user name not found");
        Balance = Double.parseDouble(prefs.getString("balance","0.0"));
        non_tracking_days = new ArrayList<>();
    }


    public static User getInstance(Context context)
    {
        if(user==null)
        {
            user=new User(context);
        }
        return user;
    }

    public int getCurrnonday() {
        Calendar cal = Calendar.getInstance();
        //int day = cal.get(Calendar.DAY_OF_YEAR);
        ArrayList<String> ret = new ArrayList<>();
        non_tracking_days = getNon_tracking_days();
        if (non_tracking_days != null) {
            for (int i = 0; i < non_tracking_days.size(); i++) {
                Calendar ne = Calendar.getInstance();
                ne.set(Integer.parseInt(non_tracking_days.get(i).substring(0, 4)), Integer.parseInt(non_tracking_days.get(i).substring(5, 7)), Integer.parseInt(non_tracking_days.get(i).substring(8, 10)));
                if (ne.get(Calendar.YEAR) > cal.get(Calendar.YEAR)) {
                    ret.add(non_tracking_days.get(i));
                } else if (ne.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
                    if (ne.get(Calendar.DAY_OF_YEAR) >= cal.get(Calendar.DAY_OF_YEAR)) {
                        ret.add(non_tracking_days.get(i));
                    }
                }

            }
        }
        return ret.size();
    }

    public ArrayList<String> getNon_tracking_days() {
        try {
            Set<String> set = prefs.getStringSet("nontrdays", null);
            non_tracking_days = new ArrayList<>(set);
            // non_tracking_days = (ArrayList<String>) ObjectSerializer.deserialize(prefs.getString("nontrdays", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (Exception e) {
            Log.e("4", "error getting noneating days");
        }
        return non_tracking_days;
    }

    public void setNon_tracking_days(String str) {
        if (!this.non_tracking_days.contains(str)) {
            //Log.d("1","ytfvyt");
            non_tracking_days.add(str);
            SharedPreferences.Editor editor = prefs.edit();
            try {
                // editor.putString("nontradays", ObjectSerializer.serialize(non_tracking_days));
                Set<String> set = new HashSet<>();
                set.addAll(non_tracking_days);
                editor.putStringSet("nontrdays", set);
                editor.commit();
            } catch (Exception e) {
                //Log.e("3", "error setting non_eating days");

            }
        }
    }

    public boolean remove_days(String str){
        boolean ret = non_tracking_days.remove(str);
        if(ret) {
            SharedPreferences.Editor editor = prefs.edit();
            try {
                Set<String> set = new HashSet<>();
                set.addAll(non_tracking_days);
                editor.putStringSet("nontrdays", set);
                editor.commit();
            } catch (Exception e) {
                //Log.e("3", "error setting non_eating days");
            }
        }
        return ret;
    }

    public void update_non_eating_days(){
        Calendar calendar = Calendar.getInstance();
        int thisyear = calendar.get(Calendar.YEAR);
        int thismonth = calendar.get(Calendar.MONTH);
        int thisday = calendar.get(Calendar.DAY_OF_MONTH);
        String today = thisyear+"/"+thismonth+"/"+thisday;
        Collections.sort(non_tracking_days);
        int i = 0;
        for(;i<non_tracking_days.size(); i++){
            int comparison_value = (non_tracking_days.get(i)).compareTo(today);
            if(comparison_value>=0){ //Stop when non_tracking_days(i) is finally greater than today;
                break;
            }
        } //remove from 0,inclusive to i, exclusive
        for(int j=0; j<i; i++){
            non_tracking_days.remove(j);
        }
        SharedPreferences.Editor editor = prefs.edit();
        try{
            //editor.clear(); //clear the preferences (hardreset)
            Set<String> set = new HashSet<>();
            set.addAll(non_tracking_days);
            editor.putStringSet("nontrdays",set);
            editor.commit();
        } catch (Exception e) {
        //Log.e("3", "error setting non_eating days");
        }
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
