package com.zby.util;

import com.zby.util.constant.TimeConstant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author ZhuBingYang
 * @date 2019-08-14
 */
public class TimeUtil {
    private static final String TAG = "TimeUtil";
    public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String YMDHM = "yyyy-MM-dd HH:mm";
    public static final String YMD = "yyyy-MM-dd";
    public static final String HM = "HH:mm";

    private static final ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<>();

    private static SimpleDateFormat getDateFormat(String pattern) {
        SimpleDateFormat format = SIMPLE_DATE_FORMAT_THREAD_LOCAL.get();
        if (format == null) {
            format = new SimpleDateFormat(pattern, Locale.getDefault());
            SIMPLE_DATE_FORMAT_THREAD_LOCAL.set(format);
        } else {
            format.applyPattern(pattern);
        }
        return format;
    }

    public static Date parseDate(String dateString, String pattern) throws ParseException {
        return getDateFormat(pattern).parse(dateString);
    }

    public static String formatDate(long dateLong, String pattern) {
        return formatDate(new Date(dateLong), pattern);
    }

    public static String formatDate(Date date, String pattern) {
        return getDateFormat(pattern).format(date);
    }

    /**
     * Returns whether the given date string is corresponds to today.
     * date equals today and time from 00:00:00 000 to 23:59:59 999.
     *
     * @param dateString the date string to determine.
     * @param pattern    date format to use.
     * @return returns {@code true} if the date after conversion corresponds to today;
     * otherwise,{@code false} is returned.
     */
    public static boolean isToday(String dateString, String pattern) {
        try {
            Date date = parseDate(dateString, pattern);

            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);

            return date.getTime() >= c.getTime().getTime() && date.getTime() < c.getTime().getTime() + TimeConstant.DAY_IN_MILLI;

        } catch (ParseException e) {
            Logger.E.log(TAG, e.getMessage() + " ,use pattern: " + pattern);
        }

        return false;
    }
}
