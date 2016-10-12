package com.example.nus.simpletodos;

import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by nus on 10/10/16.
 */

public class Helper {
    public static String getCurrentDate(Date date){
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(date);

        return currentDateTimeString;
    }

    public static String getCurrentTime(Date date){
        String currentDateTimeString = DateFormat.getTimeInstance().format(date);

        return currentDateTimeString;
    }

    public static String checkEqual(Date date){
        if (date.equals(new Date())){
            return "Day is equal";
        }else {
            return "NOT THE SAME DAY";
        }
    }

}
