package com.sup2is.accountbook.model;

public class Account {


    private final int idx;
    private final DateBundle dateBundle;
    private final String money;
    private final String method;
    private final String group;
    private final String spending;
    private final String content;
    private final String type;

    public Account(int idx ,DateBundle dateBundle, String money, String method, String group, String spending ,String content, String type) {
        this.idx = idx;
        this.dateBundle = dateBundle;
        this.money = money;
        this.method = method;
        this.group = group;
        this.spending = spending;
        this.content = content;
        this.type = type;
    }

    public DateBundle getDateBundle() {
        return dateBundle;
    }

    public int getIdx() {
        return idx;
    }

    public String getMoney() {
        return money;
    }

    public String getMethod() {
        return method;
    }

    public String getGroup() {
        return group;
    }

    public String getSpending() {
        return spending;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }
}
