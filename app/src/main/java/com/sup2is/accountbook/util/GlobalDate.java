package com.sup2is.accountbook.util;

import java.text.SimpleDateFormat;
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

    public void setCurrentTime(){
        calendar.setTime(new Date());
    }

    public int getActualMaximum() {
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public String getYearMonthToString() {
        return getYear() + "." + getMonth();
    }
    public String getYearMonthDayToString() {
        return getYear() + "." + getMonth()  + "." + getDay();
    }

    public String getTimeToString() {
        return getHour() + ":" + getMinute();
    }

    public int getYear(){
        return calendar.get(Calendar.YEAR);
    }

    public int getMonth(){
        return calendar.get(Calendar.MONTH)+1;
    }

    public int getDay(){
        return calendar.get(Calendar.DATE);
    }

    public int getDayNum() {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public int getHour() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute(){
        return calendar.get(Calendar.MINUTE);
    }

    public int getSeconds(){
        return calendar.get(Calendar.SECOND);
    }


    public String previousMonth() {
        calendar.add(calendar.MONTH, -1);
        return getYearMonthToString();
    }

    public String nextMonth() {
        calendar.add(calendar.MONTH, +1);
        return getYearMonthToString();
    }

    public int getToday() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public void setDate(int selectedYear, int selectedMonth, int selectedDay) {
        calendar.set(selectedYear,selectedMonth,selectedDay);
    }


    /**
    * @param dates is year,month,day set
    * */
    public static String getDayOfWeekToDay(String[] dates) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date dTemp = new Date(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]),Integer.parseInt(dates[2])-1);
        return sdf.format(dTemp);

    }

}
