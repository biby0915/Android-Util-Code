package com.zby.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * @author ZhuBingYang
 * @date 2019-08-21
 */
public class RegexUtil {
    /**
     * Regex of exact mobile.
     * <p>china mobile: 134(0-8), 135, 136, 137, 138, 139, 147, 150, 151, 152, 157, 158, 159, 178, 182, 183, 184, 187, 188, 198</p>
     * <p>china unicom: 130, 131, 132, 145, 155, 156, 166, 171, 175, 176, 185, 186</p>
     * <p>china telecom: 133, 153, 173, 177, 180, 181, 189, 199, 191</p>
     * <p>global star: 1349</p>
     * <p>virtual operator: 170</p>
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(16[6])|(17[0,1,3,5-8])|(18[0-9])|(19[8,9]))\\d{8}$";

    public static final String REGEX_FLOAT = "^[-\\+]?\\d*[.]\\d+$";

    /**
     * return whether input matches regex of mobile.
     *
     * @param input the phone number
     * @return {@code true} match   {@code false} not match
     */
    public static boolean isPhone(CharSequence input) {
        return patternMatch(REGEX_MOBILE, input);
    }

    /**
     * Returns whether the given CharSequence is floating-point type.
     * Strings can be converted to float or double
     */
    public static boolean isFloat(CharSequence number) {
        return patternMatch(REGEX_FLOAT, number);
    }

    /**
     * Return whether input matches the regex.
     *
     * @return {@code true} match   {@code false} not match
     */
    public static boolean patternMatch(String regex, CharSequence input) {
        return !TextUtils.isEmpty(input) && Pattern.matches(regex, input);
    }
}
