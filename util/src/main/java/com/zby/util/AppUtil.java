package com.zby.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;

/**
 * @author ZhuBingYang
 * @date 2019-08-14
 */
public class AppUtil {

    private static Application mApplication;

    public static Application getApplication() {
        if (mApplication != null) return mApplication;

        try {
            @SuppressLint("PrivateApi") Class<?> thread = Class.forName("android.app.ActivityThread");
            Object currentThread = thread.getMethod("currentActivityThread").invoke(null);
            Object app = thread.getMethod("getApplication").invoke(currentThread);
            if (app == null) {
                throw new NullPointerException("app has not init");
            }
            mApplication = (Application) app;
            return mApplication;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return mApplication;
    }

    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前app version code
     */
    public static long getAppVersionCode(Context context) {
        long appVersionCode = 0;
        try {
            PackageInfo packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            appVersionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("getAppVersionCode", e.getMessage());
        }
        return appVersionCode;
    }

    /**
     * 获取当前app version name
     */
    public static String getAppVersionName(Context context) {
        String appVersionName = "";
        try {
            PackageInfo packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            appVersionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("getAppVersionName", e.getMessage());
        }
        return appVersionName;
    }

    /**
     * Return the {@link Activity} this context is currently associated with.
     * May return null if the context associated with is not the view int the activity.
     */
    public static Activity getHostActivity(Context context) {
        if (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }

            getHostActivity(((ContextWrapper) context).getBaseContext());
        }

        return null;
    }
}
