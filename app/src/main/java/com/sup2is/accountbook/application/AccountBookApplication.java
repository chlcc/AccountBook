package com.sup2is.accountbook.application;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.sup2is.accountbook.util.SharedPreferenceManager;

public class AccountBookApplication extends Application {

    private SharedPreferenceManager spm;

    @Override
    public void onCreate() {
        super.onCreate();
        spm = SharedPreferenceManager.getInstance(this);
    }

    public SharedPreferenceManager getSpm() {
        return spm;
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
