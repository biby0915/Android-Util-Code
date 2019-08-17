package com.zby.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author ZhuBingYang
 * @date 2019-08-14
 */
public class TimeUtil {
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
}
