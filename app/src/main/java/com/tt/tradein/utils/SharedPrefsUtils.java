package com.tt.tradein.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class SharedPrefsUtils {
    public static final String SETTING = "Setting";

    public static void putValue(Context context, String key, int value) {
        Editor sp = context.getSharedPreferences(SETTING, 0).edit();
        sp.putInt(key, value);
        sp.commit();
    }

    public static void putValue(Context context, String key, boolean value) {
        Editor sp = context.getSharedPreferences(SETTING, 0).edit();
        sp.putBoolean(key, value);
        sp.commit();
    }

    public static void putValue(Context context, String key, String value) {
        Editor sp = context.getSharedPreferences(SETTING, 0).edit();
        sp.putString(key, value);
        sp.commit();
    }

    public static int getValue(Context context, String key, int defValue) {
        return context.getSharedPreferences(SETTING, 0).getInt(key, defValue);
    }

    public static boolean getValue(Context context, String key, boolean defValue) {
        return context.getSharedPreferences(SETTING, 0).getBoolean(key, defValue);
    }

    public static String getValue(Context context, String key, String defValue) {
        return context.getSharedPreferences(SETTING, 0).getString(key, defValue);
    }
}
