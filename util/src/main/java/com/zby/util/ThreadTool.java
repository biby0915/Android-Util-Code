package com.zby.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author ZhuBingYang
 * @date 2019-09-01
 */

public class ThreadTool {
    private static Handler mHandler;
    private static Executor mExecutor = Executors.newSingleThreadExecutor();

    /**
     * executes the given command in the future,the command may
     * execute in a new thread or in a pooled thread.
     *
     * @param runnable the action to run on the sub thread
     */
    public static void runOnSubThread(Runnable runnable) {
        mExecutor.execute(runnable);
    }

    /**
     * Runs the specified action on the UI thread. If the current thread is the UI
     * thread, then the action is executed immediately. If the current thread is
     * not the UI thread, the action is posted to the event queue of the UI thread.
     *
     * @param runnable the action to run on the UI thread
     */
    public static void runOnUiThread(Runnable runnable) {
        if (isOnUIThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }

    /**
     * add a runnable to the message queue,The runnable will be run on the main thread.
     *
     * @param runnable The Runnable that will be executed.
     */
    public static void post(Runnable runnable) {
        getHandler().post(runnable);
    }

    /**
     * add a runnable to the message queue,which to be run after the specified amount
     * of time elapses.
     * The runnable will be run on the main thread.
     *
     * @param runnable    The Runnable that will be executed.
     * @param delayMillis The delay (in milliseconds) until the Runnable
     *                    will be executed.
     */
    public static void postDelayed(Runnable runnable, long delayMillis) {
        getHandler().postDelayed(runnable, delayMillis);
    }

    private static Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }

    /**
     * Determine whether the program is running in the main thread
     *
     * @return whether current thread is the main thread.
     */
    public static boolean isOnUIThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    private static long getMainThreadId() {
        return Looper.getMainLooper().getThread().getId();
    }

    /**
     * Remove any pending posts of Runnable r that are in the message queue.
     *
     * @param r the specific runnable to be removed
     */
    public static void cancelTask(Runnable r) {
        getHandler().removeCallbacks(r);
    }
}

