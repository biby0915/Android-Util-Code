package com.zby.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * @author ZhuBingYang
 * @date 2019-08-14
 */
public class ScreenUtil {
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * Capture screenshot of the current application
     * <p>
     * need call {@code
     * MediaProjectionManager projectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
     * startActivityForResult(projectionManager.createScreenCaptureIntent(), requestCode);
     * } first.
     * then you can capture screen information in  {@code onActivityResult(int requestCode, int resultCode, Intent data)}
     *
     * @param activity the activity
     * @return the screen snapshot if capture succeed. otherwise, null is returned.
     */
    public static Bitmap screenShot(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.setWillNotCacheDrawing(false);
        Bitmap bmp = decorView.getDrawingCache();
        if (bmp == null) return null;
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        decorView.destroyDrawingCache();
        return ret;
    }
}