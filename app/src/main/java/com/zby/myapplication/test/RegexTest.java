package com.zby.myapplication.test;

import com.zby.util.Logger;
import com.zby.util.RegexUtil;

/**
 * author ZhuBingYang
 * date   2019-09-06
 */
public class RegexTest implements Runnable {
    private static final String TAG = "RegexTest";

    @Override
    public void run() {

        Logger.D.log(TAG, String.valueOf(RegexUtil.isFloat("1234.567")));
        Logger.D.log(TAG, String.valueOf(RegexUtil.isFloat("1234.567f")));
        Logger.D.log(TAG, String.valueOf(RegexUtil.isFloat("1234")));
        Logger.D.log(TAG, String.valueOf(RegexUtil.isFloat("asfsd")));
    }
}
