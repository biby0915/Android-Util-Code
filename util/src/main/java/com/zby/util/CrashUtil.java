package com.zby.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Build;
import android.text.TextUtils;

import java.io.File;
import java.util.Date;

/**
 * author ZhuBingYang
 * date   2019-09-19
 */
public final class CrashUtil {

    private static final String FILE_SEP = System.getProperty("file.separator");

    private static final Thread.UncaughtExceptionHandler DEFAULT_UNCAUGHT_EXCEPTION_HANDLER;
    private static final Thread.UncaughtExceptionHandler UNCAUGHT_EXCEPTION_HANDLER;

    private static OnCrashListener mOnCrashListener;

    private static Application mApplication;
    private static String mLogDir;

    static {
        DEFAULT_UNCAUGHT_EXCEPTION_HANDLER = Thread.getDefaultUncaughtExceptionHandler();
        UNCAUGHT_EXCEPTION_HANDLER = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(final Thread t, final Throwable e) {
                if (e == null) {
                    if (DEFAULT_UNCAUGHT_EXCEPTION_HANDLER != null) {
                        DEFAULT_UNCAUGHT_EXCEPTION_HANDLER.uncaughtException(t, null);
                    } else {
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                    return;
                }

                final String time = TimeUtil.formatDate(new Date(), "yyyyMMddHHmmss");
                final StringBuilder sb = new StringBuilder();
                final String head = "************* Phone Model ****************" +
                        "\nTime Of Crash      : " + TimeUtil.formatDate(new Date(), TimeUtil.YMDHMS) +
                        "\nDevice Manufacturer: " + Build.MANUFACTURER +
                        "\nDevice Model       : " + Build.MODEL +
                        "\nAndroid Version    : " + Build.VERSION.RELEASE +
                        "\nAndroid SDK        : " + Build.VERSION.SDK_INT +
                        "\nApp VersionName    : " + AppUtil.getAppVersionName(AppUtil.getApplication()) +
                        "\nApp VersionCode    : " + AppUtil.getAppVersionCode(AppUtil.getApplication()) +
                        "\n************* Phone Model ****************\n\n";
                sb.append(head).append(getCause(e));
                final String crashInfo = sb.toString();
                final String logPath = mLogDir + time + ".txt";
                File file = FileOperator.openOrCreateFile(logPath);
                if (file != null) {
                    ThreadTool.runOnSubThread(new Runnable() {
                        @Override
                        public void run() {
                            FileOperator.write(logPath, sb.toString(), false);
                        }
                    });
                }


                if (mOnCrashListener != null) {
                    mOnCrashListener.onCrash(crashInfo, e);
                }

                if (DEFAULT_UNCAUGHT_EXCEPTION_HANDLER != null) {
                    DEFAULT_UNCAUGHT_EXCEPTION_HANDLER.uncaughtException(t, e);
                }
            }
        };
    }

    private static String getCause(Throwable e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append("\n").append(element.toString());
        }
        return sb.toString();
    }

    private CrashUtil() {
    }

    @SuppressLint("MissingPermission")
    public static void init(Application application) {
        init(application, null);
    }

    public static void init(Application application, String logDir) {
        init(application, logDir, null);
    }

    public static void init(Application application, String logDir, OnCrashListener onCrashListener) {
        mApplication = application;
        if (TextUtils.isEmpty(logDir)) {
            logDir = mApplication.getExternalCacheDir() + FILE_SEP + "crash" + FILE_SEP;
        } else if (!logDir.endsWith(FILE_SEP)) {
            logDir += FILE_SEP;
        }

        mLogDir = logDir;

        mOnCrashListener = onCrashListener;
        Thread.setDefaultUncaughtExceptionHandler(UNCAUGHT_EXCEPTION_HANDLER);
    }


    public interface OnCrashListener {
        void onCrash(String crashInfo, Throwable e);
    }
}
