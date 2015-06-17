package com.unit.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesUtils {

    public static void setPreferences (Context context, String preference, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void setPreferences (Context context, String preference, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setPreferences (Context context, String preference, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static boolean getPreference (Context context, String preference, String key, boolean defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static String getPreference (Context context, String preference, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    public static int getPreference (Context context, String preference, String key, int defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }
}
