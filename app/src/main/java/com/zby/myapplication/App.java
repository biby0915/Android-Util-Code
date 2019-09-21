package com.zby.myapplication;

import android.app.Application;

import com.zby.util.CrashUtil;

/**
 * author ZhuBingYang
 * date   2019-09-19
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CrashUtil.init(this);
    }
}
