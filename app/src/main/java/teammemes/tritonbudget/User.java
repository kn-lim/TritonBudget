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
            //non_tracking_days = new ArrayList<>(set);
            this.non_tracking_days.clear();
            this.non_tracking_days.addAll(set);
            Collections.sort(this.non_tracking_days);
        } catch (Exception e) {
            Log.e("4", "error getting noneating days");
        }
        return this.non_tracking_days;
    }

    public void setNon_tracking_days(String str) {
        if (!this.non_tracking_days.contains(str)) {
            this.non_tracking_days.add(str);
            Collections.sort(non_tracking_days);
            SharedPreferences.Editor editor = prefs.edit();
            try {
                Set<String> set = new HashSet<>();
                set.addAll(this.non_tracking_days);
                editor.putStringSet("nontrdays", set);
                editor.commit();
            } catch (Exception e) {
                //Log.e("3", "error setting non_eating days");
            }
        }
    }

    public boolean remove_days(String str){
        this.non_tracking_days = this.getNon_tracking_days();
        if(this.non_tracking_days.contains(str)) {
            //Remove from the arraylist
            this.non_tracking_days.remove(str);
            //Update the preferences
            SharedPreferences.Editor editor = prefs.edit();
            try{
                //editor.clear(); //clear the preferences (hardreset)
                Set<String> set = new HashSet<>();
                set.addAll(non_tracking_days);
                editor.remove("nontrdays");
                editor.putStringSet("nontrdays",set);
                editor.commit();
                System.out.println("Updated! Removed: " + str);
                return true;
            } catch (Exception e) {
                //Log.e("3", "error setting non_eating days");
                return false;
            }
        }
        return false;
    }

    public void update_non_eating_days(){
        //Get non_tracking_days from pref, should already be sorted
        this.non_tracking_days = this.getNon_tracking_days();
        if(this.non_tracking_days.size()==0){ //Update only iff there are non_tracking_days
            return;
        }

        //Get the current date
        Calendar calendar = Calendar.getInstance();
        int int_year = calendar.get(Calendar.YEAR);
        int int_month = calendar.get(Calendar.MONTH)+1;
        int int_day = calendar.get(Calendar.DAY_OF_MONTH);
        //Formats the date to string
        String str_year = "" + int_year;
        String str_month = ""+int_month;
        String str_day = "" + int_day;
        if(int_month<10){
            str_month = "0"+str_month;
        }
        if(int_day<10){
            str_day = "0"+str_day;
        }
        String today = str_year+"/"+str_month+"/"+str_day;

        /*Find the idx of where to update*/
        //Hack: insert the current date to easier update
        Boolean remove_today = false;
        if(!this.non_tracking_days.contains(today) ){
            this.non_tracking_days.add(today);
            remove_today = true;
            Collections.sort(this.non_tracking_days);
            System.out.println("added today!");
        }
        //Start removing up until today;
        while(this.non_tracking_days.size()>0){
            //Stop when it is today
            if(this.non_tracking_days.get(0).equals(today) ){
                if(remove_today){ //Check to see if we need to remove today
                    this.non_tracking_days.remove(0); //Remove if we added for the hack
                    System.out.println("removed today!");
                }
                break;
            } else {
                this.non_tracking_days.remove(0);
            }
        }
        //Commit the changes
        SharedPreferences.Editor editor = prefs.edit();
        try{
            //editor.clear(); //clear the preferences (hardreset)
            Set<String> set = new HashSet<>();
            set.addAll(non_tracking_days);
            editor.remove("nontrdays");
            editor.putStringSet("nontrdays",set);
            editor.commit();
            System.out.println("Updated up to " + today);
        } catch (Exception e) {
            //Log.e("3", "error setting non_eating days");
        }
        return;
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