package com.sup2is.accountbook.util;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class GlobalDate {

    private static final String TAG = GlobalDate.class.getSimpleName();

    private Calendar calendar;

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

    public String getDate() {
        Log.d(TAG , calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH)+1));
        return calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH)+1);
    }

    public String previousDate() {
        calendar.add(calendar.MONTH, -1);
        return getDate();
    }

    public String nextDate() {
        calendar.add(calendar.MONTH, +1);
        return getDate();
    }


}
