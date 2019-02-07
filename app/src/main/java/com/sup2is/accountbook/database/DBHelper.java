package com.sup2is.accountbook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "account_book.db";
    public static final String TBL_NAME = "account_book";

    public DBHelper(@Nullable Context context, @Nullable String dbname, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbname, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*group == class*/
        String createSql = "CREATE TABLE " + TBL_NAME + " ("
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
                + "content TEXT"
                + ")";
        db.execSQL(createSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TBL_NAME);
        onCreate(db);
    }
}
