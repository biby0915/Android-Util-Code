package com.zby.util;

import java.util.Arrays;

/**
 * @author ZhuBingYang
 * @date 2019-09-01
 */
public class StackTrace {
    public static String getCallerName(int depth) {
        StackTraceElement caller = Thread.currentThread().getStackTrace()[depth];
        String className = caller.getClassName();
        className = className.substring(className.lastIndexOf(".") + 1);
        return className;
    }

    public static String getCallerNameWithLineNumber(int depth) {
        StackTraceElement caller = Thread.currentThread().getStackTrace()[depth];
        String className = caller.getClassName();
        className = className.substring(className.lastIndexOf(".") + 1);
        return className + " -> " + caller.getLineNumber();
    }

    public static String getCallerNameWithLineNumber(Class before) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        boolean match = false;
        for (int i = 3; i < stackTrace.length; i++) {
            StackTraceElement caller = stackTrace[i];
            String className = caller.getClassName().substring(caller.getClassName().lastIndexOf(".") + 1);
            String[] parts = className.split("\\$");
            if (Arrays.asList(parts).contains(before.getSimpleName()) || className.equals(before.getSimpleName())) {
                match = true;
            } else if (match) {
                return className + " -> " + caller.getLineNumber();
            }
        }

        return "";
    }
}
