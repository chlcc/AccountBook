package com.sup2is.accountbook.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.application.AccountBookApplication;

public class DBHelper extends SQLiteOpenHelper {

    public final Context context;
    public static final String DB_NAME = "account_book.db";
    public static final String TBL_ACCOUNT = "account_book";
    public static final String TBL_CATEGORY = "account_book_category";

    public static final int METHOD_TYPE = 1;
    public static final int GROUP_TYPE = 2;
    public static final int SPENDING_TYPE = 3;
    public static final int INCOMING_TYPE = 4;
    public static final int ADD_PHONE_NUMBER = 5;
    public static final int ADD_PHONE_NUMBER_NAME = 6;

    public DBHelper(@Nullable Context context, @Nullable String dbname, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbname, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*group == class*/
        String createSql = "CREATE TABLE IF NOT EXISTS " + TBL_ACCOUNT + " ("
                + "idx INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "year TEXT ,"
                + "month TEXT,"
                + "day TEXT,"
                + "day_of_week TEXT,"
                + "hour TEXT,"
                + "minute TEXT,"
                + "seconds TEXT,"
                + "money TEXT,"
                + "method TEXT,"
                + "class TEXT,"
                + "spending TEXT,"
                + "content TEXT,"
                + "type TEXT"
                + ")";
        db.execSQL(createSql);

        /*
        * type : 1  == method (ex: 수입, 지출 ...)
        * type : 2  == class (ex: 카드, 현금, 계좌이체 ...)
        * type : 3  == spending  (ex: 식비, 교통비 ...)
        * type : 4  == incoming (ex : 월급, 용돈 ...)
        * */
        createSql = "CREATE TABLE IF NOT EXISTS " + TBL_CATEGORY + " ("
                + "idx INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "seq INTEGER, "
                + "type INTEGER, "
                + "name TEXT "
                + ")";
        db.execSQL(createSql);
        initCategory(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS "+ TBL_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS "+ TBL_CATEGORY);
        String createSql = "CREATE TABLE " + TBL_CATEGORY + " ("
                + "idx INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "seq INTEGER, "
                + "type INTEGER, "
                + "name TEXT "
                + ")";
        db.execSQL(createSql);
        onCreate(db);
    }


    private void initCategory(SQLiteDatabase db) {


        /*
         * type : 1  == method (ex: 수입, 지출 ...)
         * type : 2  == class (ex: 카드, 현금, 계좌이체 ...)
         * type : 3  == spending  (ex: 식비, 교통비 ...)
         * type : 4  == incoming (ex : 월급, 용돈 ...)
         * */
        String[] array = context.getResources().getStringArray(R.array.default_method_array);

        for(String method : array) {
            String sql = "INSERT INTO " + DBHelper.TBL_CATEGORY + " VALUES ("
                    + "NULL" + ","
                    + "'" + getNextCategorySeq(db,METHOD_TYPE) + "' ,"
                    + "'" + METHOD_TYPE + "' ,"
                    + "'" + method+ "')";
            db.execSQL(sql);
        }

        array = context.getResources().getStringArray(R.array.default_group_array);

        for(String group : array) {
            String sql = "INSERT INTO " + DBHelper.TBL_CATEGORY + " VALUES ("
                    + "NULL" + ","
                    + "'" + getNextCategorySeq(db,GROUP_TYPE) + "' ,"
                    + "'" + GROUP_TYPE + "' ,"
                    + "'" + group+ "')";
            db.execSQL(sql);
        }

        array = context.getResources().getStringArray(R.array.default_incoming_array);

        for(String incoming : array) {
            String sql = "INSERT INTO " + DBHelper.TBL_CATEGORY + " VALUES ("
                    + "NULL" + ","
                    + "'" + getNextCategorySeq(db,INCOMING_TYPE) + "' ,"
                    + "'" + INCOMING_TYPE + "' ,"
                    + "'" + incoming+ "')";
            db.execSQL(sql);
        }


        array = context.getResources().getStringArray(R.array.default_spending_array);

        for(String spending : array) {
            String sql = "INSERT INTO " + DBHelper.TBL_CATEGORY + " VALUES ("
                    + "NULL" + ","
                    + "'" + getNextCategorySeq(db,SPENDING_TYPE) + "' ,"
                    + "'" + SPENDING_TYPE + "' ,"
                    + "'" + spending+ "')";
            db.execSQL(sql);
        }

    }

    private int getNextCategorySeq(SQLiteDatabase db , int type) {
        String sql = "SELECT MAX(seq) FROM " + DBHelper.TBL_CATEGORY + " WHERE type = '" + type + "' ";
        Cursor result = db.rawQuery(sql, null);
        if(result.moveToFirst()) {
            return result.getInt(0) + 1;
        }
        return 0;
    }
}
