package com.zby.myapplication.test;

import com.zby.util.AppUtil;
import com.zby.util.Logger;
import com.zby.util.NetworkUtil;

/**
 * author ZhuBingYang
 * date   2019-09-06
 */
public class NetTest implements Runnable {
    private static final String TAG ="NetTest";
    @Override
    public void run() {
        Logger.D.log(TAG,"ssid:" + NetworkUtil.getWifiSSID(AppUtil.getApplication()));
        Logger.D.log(TAG,"ssid:" + NetworkUtil.getWifiBSSID(AppUtil.getApplication()));
    }
}
