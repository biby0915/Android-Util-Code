package com.zby.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * @author ZhuBingYang
 * @date 2019-08-23
 */
public class LocationUtil {
    private static final String TAG = "LocationUtil";

    public LocationUtil getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * whether current application has location permissions
     */
    public boolean hasLocationPermission(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    static class SingletonHolder {
        static final LocationUtil instance = new LocationUtil();
    }
}
