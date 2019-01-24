package com.sup2is.accountbook.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sup2is.accountbook.R;
import com.sup2is.accountbook.application.AccountBookApplication;
import com.sup2is.accountbook.databinding.ActivitySplashBinding;
import com.sup2is.accountbook.util.PermissionChecker;
import com.sup2is.accountbook.util.SharedPreferenceManager;

public class SplashActivity extends Activity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    private AccountBookApplication application;

    private SharedPreferenceManager pref;

    private ActivitySplashBinding binding;

    public static boolean permissionCheck = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG , "App start");
        application = (AccountBookApplication) getApplication();
        pref = application.getSpm();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        binding.tvAppVersion.setText("Version " + application.getVersionName());


        PermissionChecker.check(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS}
        , PermissionChecker.REQUEST_PERMISSION_CODE);

        permissionProcess.run();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionChecker.REQUEST_PERMISSION_CODE:
                permissionCheck = true;
                break;
        }

    }

    private Runnable permissionProcess = new Runnable() {
        @Override
        public void run() {
            Handler handler = new Handler();
            if (permissionCheck) {
                handler.postDelayed(startProcess,1000);
            }else {
                handler.postDelayed(permissionProcess,1000);
            }
        }
    };

    private Runnable startProcess = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

}
