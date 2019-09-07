package com.zby.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * @author ZhuBingYang
 * @date 2019-08-23
 */
public class PermissionUtil {
    private static final String TAG = "PermissionUtil";


    public static boolean hasAllPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(context, permission);
//            Logger.D.log(TAG, "hasAllPermissions:" + permission + "  " + result);
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasAnyPermission(Context context, String... permissions) {
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(context, permission);
//            Logger.D.log(TAG, "hasAnyPermission:" + permission + "  " + result);
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }
}
