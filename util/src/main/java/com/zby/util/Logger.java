package com.zby.util;

import android.text.TextUtils;
import android.util.Log;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @author ZhuBingYang
 * @date 2019-08-26
 */
public class Logger {
    public static final int PRIORITY_VERBOSE = 0;
    public static final int PRIORITY_DEBUG = 1;
    public static final int PRIORITY_INFO = 2;
    public static final int PRIORITY_WARN = 3;
    public static final int PRIORITY_ERROR = 4;
    private static int mPriority = 0;

    public static void d(String tag, String msg) {
        log(PRIORITY_DEBUG, "d", tag, msg);
    }

    public static void longString(String tag, String msg) {
        if (msg != null && msg.length() > 2048) {
            int index;
            for (index = 0; index < msg.length(); index += 2048) {
                if (index + 2048 >= msg.length()) {
                    d(tag, msg.substring(index));
                } else {
                    d(tag, msg.substring(index, index + 2048));
                }
            }
        } else {
            d(tag, msg);
        }
    }

    public static void map(String tag, Map map) {
        for (Object o : map.keySet()) {
            String key = o == null ? "null" : o.toString();
            Object v = map.get(o);
            String value = v == null ? "null" : v.toString();
            d(tag, "{" + key + ": " + value + "}");
        }
    }

    public static void json(String tag, String json) {
        d(tag, TextUtil.prettyJson(" \n" + json));
    }

    public static void object(String tag, Object o) {
        if (o == null) {
            d(tag, "null");
        } else {
            d(tag, o.toString());
        }
    }

    public static void array(String tag, Object... array) {
        for (Object o : array) {
            object(tag, o);
        }
    }

    public static void list(String tag, Collection data) {
        if (data == null) {
            d(tag, "collection is null");
            return;
        }

        if (data.isEmpty()) {
            d(tag, "empty collection");
            return;
        }

        Iterator iterator = data.iterator();
        while (iterator.hasNext()) {
            object(tag, iterator.next());
        }
    }

    public static void log(int priority, String type, String tag, String msg) {
        if (priority < mPriority) {
            return;
        }

        if (TextUtils.isEmpty(msg)) {
            msg = "Trying to print an empty string";
        }

        switch (type) {
            case "d":
                Log.d(tag, msg);
                break;
            case "e":
                Log.e(tag, msg);
                break;
            case "w":
                Log.w(tag, msg);
                break;
            case "i":
                Log.i(tag, msg);
                break;
            case "v":
                Log.v(tag, msg);
                break;
        }
    }
}
