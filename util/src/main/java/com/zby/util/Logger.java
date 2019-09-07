package com.zby.util;

import android.os.Environment;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ZhuBingYang
 * @date 2019-08-26
 */
public class Logger {
    private static final String TAG = "Logger";
    static final int PRIORITY_VERBOSE = 0;
    static final int PRIORITY_DEBUG = 1;
    static final int PRIORITY_INFO = 2;
    static final int PRIORITY_WARN = 3;
    static final int PRIORITY_ERROR = 4;
    private static int mPriority = 0;

    private static boolean mEnableLog = true;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PRIORITY_VERBOSE, PRIORITY_DEBUG, PRIORITY_INFO, PRIORITY_WARN, PRIORITY_ERROR})
    @interface LogLevel {
    }

    public static D D = new D();
    public static E E = new E();
    public static I I = new I();
    public static V V = new V();
    public static W W = new W();

    private static ILog logger = new ConsoleLogger();

    public static int getPriority() {
        return mPriority;
    }

    public void setPriority(int priority) {
        mPriority = priority;
    }

    public static ILog getLogger() {
        return logger;
    }

    public static void setLogger(ILog logger) {
        Logger.logger = logger;
    }

    /**
     * log switch
     *
     * @param enable whether log should be printed
     */
    public static void setEnableLog(boolean enable) {
        mEnableLog = enable;
    }

    /**
     * @return whether log is enabled
     */
    public static boolean isEnableLog() {
        return mEnableLog;
    }

    private static class L {
        int logLevel = PRIORITY_VERBOSE;

        public void log(String tag, String msg) {
            logger.log(logLevel, tag, msg);
        }

        public void longString(String tag, String msg) {
            logger.longString(logLevel, tag, msg);
        }

        public void object(String tag, Object o) {
            logger.object(logLevel, tag, o);
        }

        public void array(String tag, Object... array) {
            logger.array(logLevel, tag, array);
        }

        public void map(String tag, Map map) {
            logger.map(logLevel, tag, map);
        }

        public void json(String tag, String json) {
            logger.json(logLevel, tag, json);
        }

        public void list(String tag, Collection data) {
            logger.list(logLevel, tag, data);
        }
    }

    public static class D extends L {
        D() {
            logLevel = PRIORITY_DEBUG;
        }
    }

    public static class E extends L {
        E() {
            logLevel = PRIORITY_ERROR;
        }
    }

    public static class W extends L {
        W() {
            logLevel = PRIORITY_WARN;
        }
    }

    public static class I extends L {
        I() {
            logLevel = PRIORITY_INFO;
        }
    }

    public static class V extends L {
        V() {
            logLevel = PRIORITY_VERBOSE;
        }
    }

    public interface ILog {
        void log(@LogLevel int log_level, String tag, String msg);

        void longString(@LogLevel int log_level, String tag, String msg);

        void object(@LogLevel int log_level, String tag, Object o);

        void array(@LogLevel int log_level, String tag, Object... array);

        void map(@LogLevel int log_level, String tag, Map map);

        void json(@LogLevel int log_level, String tag, String json);

        void list(@LogLevel int log_level, String tag, Collection data);
    }

    public static class ConsoleLogger implements ILog {
        /**
         * Segmented printing of a long string.
         * The length of each segment is 2048, due to internal log max length limit and
         * chinese takes up two characters
         *
         * @param tag Used to identify the source of a log message.
         * @param msg the content to be print in the logcat
         */
        public void longString(@LogLevel int logLevel, String tag, String msg) {
            int segmentSize = 2 * 1024;
            if (msg != null && msg.length() > segmentSize) {
                int index;
                for (index = 0; index < msg.length(); index += segmentSize) {
                    if (index + segmentSize >= msg.length()) {
                        log(logLevel, tag, msg.substring(index));
                    } else {
                        log(logLevel, tag, msg.substring(index, index + segmentSize));
                    }
                }
            } else {
                log(logLevel, tag, msg);
            }
        }

        /**
         * print the data in the map.
         *
         * @param logLevel the log type
         * @param tag      Used to identify the source of a log message.
         * @param map      the content to be print in the logcat
         */
        public void map(@LogLevel int logLevel, String tag, Map map) {
            //Reduce duplicate calls
            if (TextUtils.isEmpty(tag)) {
                tag = StackTrace.getCallerNameWithLineNumber(Logger.class);
            }
            for (Object o : map.keySet()) {
                String key = o == null ? "null" : o.toString();
                Object v = map.get(o);
                String value = v == null ? "null" : v.toString();
                log(logLevel, tag, "{" + key + ": " + value + "}");
            }
        }

        /**
         * send a log message.
         * the json string will be formatted for clearer display.
         *
         * @param logLevel the log type
         * @param tag      Used to identify the source of a log message.
         * @param json     the content to be print in the logcat
         */
        public void json(@LogLevel int logLevel, String tag, String json) {
            log(logLevel, tag, " \n" + TextUtil.prettyJson(json));
        }

        /**
         * send a log message.
         *
         * @param logLevel the log type
         * @param tag      Used to identify the source of a log message.
         * @param o        the string representation of the argument
         */
        public void object(@LogLevel int logLevel, String tag, Object o) {
            log(logLevel, tag, String.valueOf(o));
        }

        /**
         * print the data in the array.
         *
         * @param logLevel the log type
         * @param tag      Used to identify the source of a log message.
         * @param array    the content to be print in the logcat
         */
        public void array(@LogLevel int logLevel, String tag, Object... array) {
            //Reduce duplicate calls
            if (TextUtils.isEmpty(tag)) {
                tag = StackTrace.getCallerNameWithLineNumber(Logger.class);
            }
            for (Object o : array) {
                object(logLevel, tag, o);
            }
        }

        /**
         * print the data in the collection.
         *
         * @param logLevel the log type
         * @param tag      Used to identify the source of a log message.
         * @param data     the content to be print in the logcat
         */
        public void list(@LogLevel int logLevel, String tag, Collection data) {
            if (data == null) {
                log(logLevel, tag, "collection is null");
                return;
            }

            if (data.isEmpty()) {
                log(logLevel, tag, "empty collection");
                return;
            }
            //Reduce duplicate calls
            if (TextUtils.isEmpty(tag)) {
                tag = StackTrace.getCallerNameWithLineNumber(Logger.class);
            }
            for (Object o : data) {
                object(logLevel, tag, o);
            }
        }

        /**
         * send a log message
         *
         * @param log_level log type in {@link Log#DEBUG},{@link Log#VERBOSE},
         *                  {@link Log#INFO},{@link Log#ERROR},{@link Log#WARN},
         * @param tag       Used to identify the source of a log message.
         * @param msg       the content to be print in the logcat
         */
        public void log(@LogLevel int log_level, String tag, String msg) {
            if (!mEnableLog) {
                return;
            }

            if (mPriority > log_level) {
                return;
            }

            if (TextUtils.isEmpty(tag)) {
                tag = StackTrace.getCallerNameWithLineNumber(Logger.class);
            }

            if (TextUtils.isEmpty(tag)) {
                tag = TAG;
            }

            if (TextUtils.isEmpty(msg)) {
                msg = "Trying to print an empty string.";
            }

            switch (log_level) {
                case PRIORITY_DEBUG:
                    Log.d(tag, msg);
                    break;
                case PRIORITY_ERROR:
                    Log.e(tag, msg);
                    break;
                case PRIORITY_WARN:
                    Log.w(tag, msg);
                    break;
                case PRIORITY_INFO:
                    Log.i(tag, msg);
                    break;
                case PRIORITY_VERBOSE:
                    Log.v(tag, msg);
                    break;
            }
        }
    }

    public static class FileLogger extends ConsoleLogger {
        private String mFilePath;
        private List<String> mLogs = new ArrayList<>();
        private final String[] TAGS = {"V", "D", "I", "W", "E"};
        private boolean mKeepWriting = true;

        public FileLogger() {
            this(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + AppUtil.getAppName(AppUtil.getApplication()) + "/logcat/" + TimeUtil.formatDate(new Date(), "yyyyMMddHHmmss") + ".log");
        }

        public FileLogger(String filePath) {
            this.mFilePath = filePath;

            ThreadTool.runOnSubThread(new Runnable() {
                @Override
                public void run() {
                    while (mKeepWriting) {
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (!mLogs.isEmpty()) {
                            List<String> temp = new ArrayList<>(mLogs);
                            mLogs.clear();
                            FileOperator.write(mFilePath, temp, true);
                        }
                    }
                }
            });
        }

        public void dumpAndDie() {
            mKeepWriting = false;
            FileOperator.write(mFilePath, mLogs, true);
        }

        @Override
        public void log(int log_level, String tag, String msg) {
            super.log(log_level, tag, msg);

            if (mKeepWriting) {
                mLogs.add(TAGS[log_level] + "/" + tag + ": " + msg + "\n");
            }
        }
    }
}
