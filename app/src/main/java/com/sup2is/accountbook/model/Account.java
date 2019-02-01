package com.sup2is.accountbook.model;

import java.util.Date;

public class Account {

    private final DateBundle dateBundle;
    private final String money;
    private final String method;
    private final String group;
    private final String spending;
    private String content;

    public Account(DateBundle dateBundle, String money, String method, String group, String spending ,String content) {
        this.dateBundle = dateBundle;
        this.money = money;
        this.method = method;
        this.group = group;
        this.spending = spending;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DateBundle getDateBundle() {
        return dateBundle;
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

}
