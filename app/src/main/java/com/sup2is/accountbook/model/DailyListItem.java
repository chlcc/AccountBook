package com.sup2is.accountbook.model;

public class DailyListItem {

    private String day;
    private String time;
    private String week;
    private String money;
    private String content;
    private String group;
    private String method;

    public DailyListItem(String day, String time, String week, String money, String content, String group, String method) {
        this.day = day;
        this.time = time;
        this.week = week;
        this.money = money;
        this.content = content;
        this.group = group;
        this.method = method;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
