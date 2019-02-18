package com.sup2is.accountbook.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.sup2is.accountbook.model.Account;
import com.sup2is.accountbook.model.Category;
import com.sup2is.accountbook.model.DateBundle;

import java.util.ArrayList;

public class DBManager {

    private static final String TAG = "###" + DBHelper.class.getSimpleName() + " : ";
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
                + "'" +item.getDateBundle().getYear()+ "' ,"
                + "'" +item.getDateBundle().getMonth()+ "' ,"
                + "'" +item.getDateBundle().getDay()+ "' ,"
                + "'" +item.getDateBundle().getDayOfWeek()+ "' ,"
                + "'" +item.getDateBundle().getHour()+ "' ,"
                + "'" +item.getDateBundle().getMinute()+ "' ,"
                + "'" +item.getDateBundle().getSeconds()+ "' ,"
                + "'" +item.getMoney()+ "' ,"
                + "'" +item.getMethod()+ "' ,"
                + "'" +item.getGroup()+ "' ,"
                + "'" +item.getSpending()+ "' ,"
                + "'" +item.getContent()+ "' ,"
                + "'" +item.getType()+ "')";
        db.execSQL(sql);
}

    public ArrayList<Account> selectByDate(DateBundle bundle) {

        String sql = "SELECT * FROM " + DBHelper.TBL_ACCOUNT + " WHERE "
                + "year = " + "'" + bundle.getYear() + "' " + "AND "
                + "month = " + "'"+ bundle.getMonth() + "' "
                + "ORDER BY CAST(day AS INTEGER) DESC , CAST(hour AS INTEGER) , CAST(minute AS INTEGER) ASC";

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


    public String getItemType(DateBundle dateBundle) {
        //exist = 1 or not = 0
        String sql = "SELECT EXISTS ( SELECT * FROM " + DBHelper.TBL_ACCOUNT + " WHERE "
                + "year = " + "'" + dateBundle.getYear() + "' " + "AND "
                + "month = " + "'"+ dateBundle.getMonth() + "' " + "AND "
                + "day = " + "'"+ dateBundle.getDay() + "' )";
        Cursor results = db.rawQuery(sql,null);
        results.moveToFirst();

        if(results.getInt(0) == 0) {
            return results.getString(0);
        }

        //exist ...
        Account account = selectByDateToFirstItem(dateBundle);

        if(account != null) {
            //minute만 비교하면 안됨 ... 시도 비교해야함
            if(Integer.parseInt(account.getDateBundle().getHour()+account.getDateBundle().getMinute()) > Integer.parseInt(dateBundle.getHour()+dateBundle.getMinute())){
                //만약 db에 있는 가장 최신데이터보다 hour+minute값이 빠르면 type들 초기화 후 return 0 (header)
                sql = "UPDATE " + DBHelper.TBL_ACCOUNT + " SET type = '" + 1 + "' WHERE "
                        + "year = " + "'" + dateBundle.getYear() + "' " + "AND "
                        + "month = " + "'"+ dateBundle.getMonth() + "' " + "AND "
                        + "day = " + "'"+ dateBundle.getDay() + "' ";
                db.execSQL(sql);
                return "0";
            }
        }
            return results.getString(0);
    }

    public int getNextAutoIncrement(String tableName) {
        String sql = "SELECT * FROM SQLITE_SEQUENCE WHERE name = " + "'" + tableName + "'";
        Cursor results = db.rawQuery(sql,null);
        if(results.moveToFirst()){
            return results.getInt(results.getColumnIndex("seq"));
        }
        return 0;
    }

    public int getNextCategorySeq(int type) {
        String sql = "SELECT MAX(seq) FROM " + DBHelper.TBL_CATEGORY + " WHERE type = '" + type + "' ";
        Cursor result = db.rawQuery(sql, null);
        if(result.moveToFirst()) {
            return result.getInt(0) + 1;
        }
        return 0;
    }

    public void modifyItem(Account account) {
        String sql = "UPDATE " + DBHelper.TBL_ACCOUNT + " SET "
                + "year = " + "'" + account.getDateBundle().getYear() + "' ,"
                + "month = " + "'" + account.getDateBundle().getMonth() + "' ,"
                + "day = " + "'" + account.getDateBundle().getDay() + "' ,"
                + "day_of_week = " + "'" + account.getDateBundle().getDayOfWeek() + "' ,"
                + "hour = " + "'" + account.getDateBundle().getHour() + "' ,"
                + "minute = " + "'" + account.getDateBundle().getMinute() + "' ,"
                + "seconds = " + "'" + account.getDateBundle().getSeconds() + "' ,"
                + "money = " + "'" + account.getMoney() + "' ,"
                + "method = " + "'" + account.getMethod() + "' ,"
                + "class = " + "'" + account.getGroup() + "' ,"
                + "spending = " + "'" + account.getSpending() + "' ,"
                + "content = " + "'" + account.getContent() + "' ,"
                + "type = " + "'" + account.getType() + "'"
                + "WHERE idx =" + "'" + account.getIdx() + "'";
        db.execSQL(sql);
    }

    private Account selectByDateToFirstItem(DateBundle bundle) {
        String sql = "SELECT * FROM " + DBHelper.TBL_ACCOUNT + " WHERE "
                + "year = " + "'" + bundle.getYear() + "' " + "AND "
                + "month = " + "'"+ bundle.getMonth() + "' " + "AND "
                + "day = " + "'"+ bundle.getDay() + "' "
                + "ORDER BY CAST(hour AS INTEGER) , CAST(minute AS INTEGER) ASC";

        Cursor results = db.rawQuery(sql,null);
        if(results.moveToFirst()) {
            DateBundle dateBundle = new DateBundle(
                    results.getString(results.getColumnIndex("year")),
                    results.getString(results.getColumnIndex("month")),
                    results.getString(results.getColumnIndex("day")),
                    results.getString(results.getColumnIndex("day_of_week")),
                    results.getString(results.getColumnIndex("hour")),
                    results.getString(results.getColumnIndex("minute")),
                    results.getString(results.getColumnIndex("seconds"))
            );
            return new Account(
                    results.getInt(results.getColumnIndex("idx"))
                    , dateBundle,
                    results.getString(results.getColumnIndex("money")),
                    results.getString(results.getColumnIndex("method")),
                    results.getString(results.getColumnIndex("class")),
                    results.getString(results.getColumnIndex("spending")),
                    results.getString(results.getColumnIndex("content")),
                    results.getString(results.getColumnIndex("type")));
        }
        return null;
    }

    public void reorderingData(DateBundle bundle) {
        Account item = selectByDateToFirstItem(bundle);
        if(item != null) {
            //만약 날짜가 변경된다면 객체가 있던 날짜중 가장 첫번째 값의 type은 0이 되어야함 (0번 데이터 변경시)
            String sql = "UPDATE " + DBHelper.TBL_ACCOUNT + " SET type = '" + 0 +"' WHERE idx = '" + item.getIdx() + "'";
            db.execSQL(sql);
        }
    }

    public void deleteItem(int idx) {
        String sql = "DELETE FROM " + DBHelper.TBL_ACCOUNT + " WHERE idx =" + "'" + idx + "'";
        db.execSQL(sql);
    }

    public Account selectByIdx(int idx) {
        String sql = "SELECT * FROM " + DBHelper.TBL_ACCOUNT + " WHERE idx = " + "'" + idx + "' ";
        Cursor results = db.rawQuery(sql,null);
        results.moveToFirst();
        DateBundle dateBundle = new DateBundle(
                results.getString(results.getColumnIndex("year")),
                results.getString(results.getColumnIndex("month")),
                results.getString(results.getColumnIndex("day")),
                results.getString(results.getColumnIndex("day_of_week")),
                results.getString(results.getColumnIndex("hour")),
                results.getString(results.getColumnIndex("minute")),
                results.getString(results.getColumnIndex("seconds"))
        );
        return new Account(
                results.getInt(results.getColumnIndex("idx"))
                ,dateBundle,
                results.getString(results.getColumnIndex("money")),
                results.getString(results.getColumnIndex("method")),
                results.getString(results.getColumnIndex("class")),
                results.getString(results.getColumnIndex("spending")),
                results.getString(results.getColumnIndex("content")),
                results.getString(results.getColumnIndex("type")));
    }

    public void temp() {
//        String sql = "UPDATE " + DBHelper.TBL_ACCOUNT + " SET type = 0 WHERE + idx = 13";
//        db.execSQL(sql);

    }


    public ArrayList<String> getSpinnerList(int type) {
        String sql = "SELECT * FROM " + DBHelper.TBL_CATEGORY + " WHERE type = " + "'" + type + "' ";
        Cursor results = db.rawQuery(sql,null);
        ArrayList<String> list = new ArrayList<>();
        results.moveToFirst();
        while (!results.isAfterLast()) {
            list.add(results.getString(results.getColumnIndex("name")));
            results.moveToNext();
        }
        return list;
    }

    public void insertCategory(Category category) {
        String sql = "INSERT INTO " + DBHelper.TBL_CATEGORY + " VALUES ("
                + "NULL" + ","
                + "'" +category.getSeq()+ "' ,"
                + "'" +category.getType()+ "' ,"
                + "'" +category.getName() + "')";
        db.execSQL(sql);
    }
}
