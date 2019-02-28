package com.sup2is.accountbook.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferenceManager {

    public static final String USE_FIRST = "USE_FIRST";
    public static final String USE_SMS_ON_OFF = "USE_SMS_ON_OFF";

    private static SharedPreferenceManager instance;

    private static SharedPreferences pref;

    private static SharedPreferences.Editor editor;

    private static final String TAG = SharedPreferenceManager.class.getSimpleName();

    private SharedPreferenceManager(Context context) {

        Log.d(TAG, "SharedPreferences Name is " + context.getPackageName());
        pref = context.getSharedPreferences(context.getPackageName() , Context.MODE_PRIVATE);
        editor = pref.edit();

    }

    public static SharedPreferenceManager getInstance(Context context) {
        if(instance == null) {
            instance = new SharedPreferenceManager(context);
        }
        return instance;
    }

    public void putString (String key , String value) {
        if (pref == null) {
            throw new NullPointerException("SharedPreferences is null.");
        }

        if (editor == null) {
            editor = pref.edit();
        }

        Log.d(TAG, "SharedPreferences putString key : " + key + "|| value : " + value);

        editor.putString(key,value);
        editor.commit();
    }


    public void putBoolean (String key, Boolean bool) {
        if (pref == null) {
            throw new NullPointerException("SharedPreferences is null.");
        }

        if (editor == null) {
            editor = pref.edit();
        }

        Log.d(TAG, "SharedPreferences putBoolean key : " + key + "|| value : " + bool);
        editor.putBoolean(key,bool);
        editor.commit();
    }

    public String getString (String key, String defaultValue) {
        if (pref == null) {
            throw new NullPointerException("SharedPreferences is null.");
        }
        Log.d(TAG, "SharedPreferences getString key : " + key);
        return pref.getString(key, defaultValue);
    }


    public boolean getBoolean (String key, boolean defaultValue) {
        if (pref == null) {
            throw new NullPointerException("SharedPreferences is null.");
        }
        Log.d(TAG, "SharedPreferences getString key : " + key);
        return pref.getBoolean(key, defaultValue);
    }

}
