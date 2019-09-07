package com.zby.myapplication.test;

import com.zby.util.Logger;
import com.zby.util.TimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * author ZhuBingYang
 * date   2019-09-06
 */
public class TimeUtilTest implements Runnable {
    private static final String TAG = "TimeUtilTest";

    @Override
    public void run() {

        long current = System.currentTimeMillis();

        SimpleDateFormat df = new SimpleDateFormat(TimeUtil.YMDHMS);
        try {
            for (int i = 0; i < 100000; i++) {
                df.parse("2019-08-28 12:34:56");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Logger.D.log(TAG, String.valueOf(System.currentTimeMillis() - current));

        current = System.currentTimeMillis();

        try {
            for (int i = 0; i < 100000; i++) {
                TimeUtil.parseDate("2019-08-28 12:34:56", TimeUtil.YMDHMS);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Logger.D.log(TAG, String.valueOf(System.currentTimeMillis() - current));

        Logger.E.log(null, String.valueOf(TimeUtil.isToday("2019-09-04", TimeUtil.YMD)));
    }
}
