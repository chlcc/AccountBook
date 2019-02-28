package com.sup2is.accountbook.util;

import java.text.DecimalFormat;

public class CommaFormatter {

    private static final DecimalFormat formatter = new DecimalFormat("###,###");

    public static String comma(long money) {
        return formatter.format(money);
    }

    public static String unComma(String money) {return money.replace("," ,"");}
}
