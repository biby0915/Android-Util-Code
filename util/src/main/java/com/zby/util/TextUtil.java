package com.zby.util;

import android.text.TextUtils;

/**
 * @author ZhuBingYang
 * @date 2019-08-14
 */
public class TextUtil {

    public static String stringValueOf(Object o) {
        if (o == null) {
            return "";
        }

        return o.toString();
    }

    public static boolean isEmpty(String s) {
        return TextUtils.isEmpty(s) && !"null".equals(s);
    }

    public static String prettyJson(String json) {
        int level = 0;
        StringBuilder result = new StringBuilder();
        for (int index = 0; index < json.length(); index++)
        {
            char c = json.charAt(index);

            if (level > 0 && '\n' == result.charAt(result.length() - 1)) {
                result.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    result.append(c).append("\n");
                    level++;
                    break;
                case ',':
                    result.append(c).append("\n");
                    break;
                case '}':
                case ']':
                    result.append("\n");
                    level--;
                    result.append(getLevelStr(level));
                    result.append(c);
                    break;
                default:
                    result.append(c);
                    break;
            }

        }

        return result.toString();
    }

    private static String getLevelStr(int level) {
        StringBuilder levelStr = new StringBuilder();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }
}
