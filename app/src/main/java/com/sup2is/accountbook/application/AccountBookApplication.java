package com.sup2is.accountbook.application;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.sup2is.accountbook.database.DBManager;
import com.sup2is.accountbook.util.SharedPreferenceManager;

public class AccountBookApplication extends Application {

    private SharedPreferenceManager spm;
    private DBManager dbManager;

    @Override
    public void onCreate() {
        super.onCreate();
        spm = SharedPreferenceManager.getInstance(this);
        dbManager = new DBManager(getApplicationContext(),1);
        dbManager.temp();
    }

    public SharedPreferenceManager getSpm() {
        return spm;
    }

    public DBManager getDbManager() {
        return dbManager;
    }

    public String getVersionName() {
        String version = null;
        try {
            PackageInfo i = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = i.versionName;
        } catch(PackageManager.NameNotFoundException e) { }
        return version;
    }
}
