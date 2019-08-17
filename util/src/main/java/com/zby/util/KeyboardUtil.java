package com.zby.util;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import static com.zby.util.AppUtil.getApplication;

/**
 * @author ZhuBingYang
 * @date 2019-08-14
 */
public class KeyboardUtil {
    public static void hideKeyBoard(View view) {
        hideKeyBoard(view.getWindowToken());
    }

    public static void hideKeyBoard(IBinder windowToken) {
        InputMethodManager imm = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive()) {
            imm.hideSoftInputFromWindow(windowToken, 0);
        }
    }

    public static void showKeyBoard(View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }
}
