package com.sup2is.accountbook.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PermissionChecker {

    public static final int REQUEST_PERMISSION_CODE = 0010;

    private static final String TAG = PermissionChecker.class.getSimpleName();

    public static void check (Activity activity , String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            List<String> list = new ArrayList<>();
            Collections.addAll(list, permissions);

            for (String permission : permissions) {
                if (activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, permission + " permission is granted");
                    list.remove(permission);
                }
            }
            permissions = list.toArray(new String[list.size()]);
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        }
    }
}