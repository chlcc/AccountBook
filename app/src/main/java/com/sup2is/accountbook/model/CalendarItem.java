package com.sup2is.accountbook.model;

public class CalendarItem {

    private String day;
    private long spendingMoney;
    private long incomingMoney;

    public CalendarItem(String day, long spendingMoney, long incomingMoney) {
        this.day = day;
        this.spendingMoney = spendingMoney;
        this.incomingMoney = incomingMoney;
    }


    public String getDay() {
        return day;
    }

    public long getSpendingMoney() {
        return spendingMoney;
    }

    public long getIncomingMoney() {
        return incomingMoney;
    }
}
