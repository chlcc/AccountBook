package com.sup2is.accountbook;

import com.sup2is.accountbook.util.GlobalDate;

import org.junit.Test;

import java.sql.SQLOutput;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void getDate() {

        GlobalDate globalDate = GlobalDate.getInstance();
        globalDate.nextDate();
        System.out.println(globalDate.getDate());
        System.out.println(globalDate.getYear());
        System.out.println(globalDate.getDay());
        System.out.println(globalDate.getDayNum());
        System.out.println(globalDate.getMonth()+1);


        System.out.println(globalDate.getCalendarInstance().getActualMaximum(Calendar.DAY_OF_MONTH));

    }
}