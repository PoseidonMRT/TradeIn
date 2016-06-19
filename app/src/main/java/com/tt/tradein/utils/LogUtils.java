package com.tt.tradein.utils;

import android.util.Log;

public class LogUtils {
    public static String TAG = "message";
    public static boolean isTest = true;

    public static void d(String key, String value) {
        if (isTest) {
            Log.d(key, value);
        }
    }

    public static void i(String key, String value) {
        if (isTest) {
            Log.i(key, value);
        }
    }

    public static void e(String key, String value) {
        if (isTest) {
            Log.e(key, value);
        }
    }

    public static void log(String tag, String info) {
        StackTraceElement[] ste = new Throwable().getStackTrace();
        if (isTest) {
            StackTraceElement s = ste[1];
            Log.e(tag, String.format("======[%s][%s][%s]=====%s", new Object[]{s.getClassName(), Integer.valueOf(s.getLineNumber()), s.getMethodName(), info}));
        }
    }

    public static void w(String key, String value) {
        if (isTest) {
            Log.w(key, value);
        }
    }

    public static void w(String key, Throwable tr) {
        if (isTest) {
            Log.w(key, tr);
        }
    }

    public static void w(String key, String value, Throwable tr) {
        if (isTest) {
            Log.w(key, value, tr);
        }
    }

    public static void d(String value) {
        if (isTest) {
            Log.d(TAG, value);
        }
    }

    public static void i(String value) {
        if (isTest) {
            Log.i(TAG, value);
        }
    }

    public static void e(String value) {
        if (isTest) {
            Log.e(TAG, value);
        }
    }

    public static void log(String info) {
        StackTraceElement[] ste = new Throwable().getStackTrace();
        if (isTest) {
            StackTraceElement s = ste[1];
            Log.e(TAG, String.format("======[%s][%s][%s]=====%s", new Object[]{s.getClassName(), Integer.valueOf(s.getLineNumber()), s.getMethodName(), info}));
        }
    }

    public static void w(String value) {
        if (isTest) {
            Log.w(TAG, value);
        }
    }

    public static void w(Throwable tr) {
        if (isTest) {
            Log.w(TAG, tr);
        }
    }
}
