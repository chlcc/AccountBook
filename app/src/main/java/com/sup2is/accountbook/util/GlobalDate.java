package com.sup2is.accountbook.util;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class GlobalDate {

    private static final String TAG = GlobalDate.class.getSimpleName();

    private final Calendar calendar;

    private static GlobalDate instance;

    public static GlobalDate getInstance() {
        if(instance == null) {
            instance = new GlobalDate();
        }
        return instance;
    }

    private GlobalDate() {
        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
    }

    public int getActualMaximum() {
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public String getDate() {
        Log.d(TAG , calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH)+1));
        return calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH)+1);
    }

    public int getYear(){
        return calendar.get(Calendar.YEAR);
    }

    public int getMonth(){
        return calendar.get(Calendar.MONTH);
    }

    public int getDay(){
        return calendar.get(Calendar.DATE);
    }

    public int getDayNum() {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public String previousMonth() {
        calendar.add(calendar.MONTH, -1);
        return getDate();
    }

    public String nextMonth() {
        calendar.add(calendar.MONTH, +1);
        return getDate();
    }

    public int getToday() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

}
