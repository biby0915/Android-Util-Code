package com.zby.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * @author ZhuBingYang
 * @date 2019-09-04
 */
public class SettingRouter {

    public static void toMainSetting(Activity context) {
        Intent intent;
        Uri packageURI = Uri.parse("package:" + context.getPackageName());
        intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        context.startActivity(intent);
    }

    public static void toLocationSourceSetting(Activity activity) {
        activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }
}
