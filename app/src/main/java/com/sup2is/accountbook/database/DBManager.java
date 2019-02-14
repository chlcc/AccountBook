package com.sup2is.accountbook.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sup2is.accountbook.model.Account;
import com.sup2is.accountbook.model.DateBundle;

import java.util.ArrayList;

public class DBManager {

    private final DBHelper dbHelper;

    private final SQLiteDatabase db;


    public DBManager(Context context, int version) {
        this(context,null,version);
    }

    public DBManager(Context context, SQLiteDatabase.CursorFactory factory, int version) {
        this.dbHelper = new DBHelper(context,DBHelper.DB_NAME,factory,version);
        this.db = dbHelper.getWritableDatabase();
    }

    public void insertItem(Account item) {
        String sql = "INSERT INTO " + DBHelper.TBL_ACCOUNT + " VALUES ("
                + "NULL" + ","
                + "'" +item.getDateBundle().getYear()+ "'" + ","
                + "'" +item.getDateBundle().getMonth()+ "'" + ","
                + "'" +item.getDateBundle().getDay()+ "'" + ","
                + "'" +item.getDateBundle().getDayOfWeek()+ "'" + ","
                + "'" +item.getDateBundle().getHour()+ "'" + ","
                + "'" +item.getDateBundle().getMinute()+ "'" + ","
                + "'" +item.getDateBundle().getSeconds()+ "'" + ","
                + "'" +item.getMoney()+ "'" + ","
                + "'" +item.getMethod()+ "'" + ","
                + "'" +item.getGroup()+ "'" + ","
                + "'" +item.getSpending()+ "'" + ","
                + "'" +item.getContent()+ "'" + ","
                + "'" +item.getType()+ "'" + ")";
        db.execSQL(sql);
    }



    public ArrayList<Account> selectByDate(DateBundle bundle) {

        String sql = "SELECT * FROM " + DBHelper.TBL_ACCOUNT + " WHERE "
                + "year = " + "'" + bundle.getYear() + "' " + "AND "
                + "month = " + "'"+ bundle.getMonth() + "' "
                + "ORDER BY CAST(day AS REAL) DESC";

        Cursor results = db.rawQuery(sql,null);
        results.moveToFirst();
        
        ArrayList<Account> accounts = new ArrayList<>();
        DateBundle dateBundle;

        while (!results.isAfterLast()) {
            dateBundle = new DateBundle(
                    results.getString(results.getColumnIndex("year")),
                    results.getString(results.getColumnIndex("month")),
                    results.getString(results.getColumnIndex("day")),
                    results.getString(results.getColumnIndex("day_of_week")),
                    results.getString(results.getColumnIndex("hour")),
                    results.getString(results.getColumnIndex("minute")),
                    results.getString(results.getColumnIndex("seconds"))
                    );

            accounts.add(new Account(
                    results.getInt(results.getColumnIndex("idx"))
                    ,dateBundle,
                    results.getString(results.getColumnIndex("money")),
                    results.getString(results.getColumnIndex("method")),
                    results.getString(results.getColumnIndex("class")),
                    results.getString(results.getColumnIndex("spending")),
                    results.getString(results.getColumnIndex("content")),
                    results.getString(results.getColumnIndex("type")))
            );
            results.moveToNext();
        }
        results.close();
        return accounts;
    }

    public String getItemType(String day) {
        String sql = "SELECT EXISTS ( SELECT * FROM " + DBHelper.TBL_ACCOUNT + " WHERE " + "day = " + "'" + day +"')";
        Cursor results = db.rawQuery(sql,null);
        results.moveToFirst();
        return results.getString(0);
    }

    public int getNextAutoIncrement() {
        String sql = "SELECT * FROM SQLITE_SEQUENCE";
        Cursor results = db.rawQuery(sql,null);
        results.moveToFirst();
        return results.getInt(results.getColumnIndex("seq"));
    }
}
