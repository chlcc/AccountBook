package com.sup2is.accountbook.model;

public class DateBundle {

    private final String year;
    private final String month;
    private final String day;
    private final String dayOfWeek;
    private final String hour;
    private final String minute;
    private final String seconds;


    /**
     * @param dates is year, month, day set
     * @param times is hour, minute set
     * */
    public DateBundle(String[] dates,String dayOfWeek, String[] times) {
      this(dates[0],dates[1],dates[2],dayOfWeek,times[0],times[1],"0");
    }

    public DateBundle(String year, String month, String day, String dayOfWeek, String hour, String minute, String seconds) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayOfWeek = dayOfWeek;
        this.hour = hour;
        this.minute = minute;
        this.seconds = seconds;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public String getSeconds() {
        return seconds;
    }
}
