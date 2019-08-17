package com.zby.util;

import android.text.TextUtils;

/**
 * @author ZhuBingYang
 * @date 2019-08-14
 */
public class TextUtil {
    /**
     * 对象转换为字符串
     * String.valueOf会将null转换成"null" 期望null显示空白
     *
     * @param o
     * @return
     */
    public static String stringValueOf(Object o) {
        if (o == null) {
            return "";
        }

        return o.toString();
    }

    public static boolean isEmpty(String s) {
        return TextUtils.isEmpty(s) && !"null".equals(s);
    }
}
