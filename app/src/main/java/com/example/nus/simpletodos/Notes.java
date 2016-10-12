package com.example.nus.simpletodos;

import android.text.format.DateUtils;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nus on 10/10/16.
 */

public class Notes extends RealmObject {
    //@PrimaryKey
    //private int id;

    private String content;
    private String date;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return getLocalDate();
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOriginDate(){
        return this.date;
    }

    private String getLocalDate() {
        Date fDate = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+00:00'"); //2016-10-11T07:15:38+00:00
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            fDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(fDate);
        cal2.setTime(new Date());
        boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);

        if ( sameDay ) {
            return Helper.getCurrentTime(fDate);
        }else{
            return Helper.getCurrentDate(fDate);
        }
    }
}
