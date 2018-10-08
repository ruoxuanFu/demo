package com.ikcrm.testapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtils {

    public static String getPrefString(Context context, String key, final String defaultValue) {
        if(context == null){
            return "";
        }
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getString(key, defaultValue);
    }

    public static void setPrefString(Context context, final String key, final String value) {
        if(context == null){
            return;
        }
        try{
            final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            settings.edit().putString(key, value).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setPrefBoolean(Context context, final String key, final boolean value) {
        if(context == null){
            return;
        }
        try{
            final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            settings.edit().putBoolean(key, value).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean getPrefBoolean(Context context, final String key, final boolean defaultValue) {
        if(context == null){
            return false;
        }
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getBoolean(key, defaultValue);
    }

    public static void setPrefInt(Context context, final String key, final int value) {
        if(context == null){
            return;
        }
        try{
            final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            settings.edit().putInt(key, value).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static int getPrefInt(Context context, final String key, final int defaultValue) {
        if(context == null){
            return 0;
        }
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getInt(key, defaultValue);
    }

    public static void setPrefFloat(Context context, final String key, final float value) {
        if(context == null){
            return;
        }
        try{
            final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            settings.edit().putFloat(key, value).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static float getPrefFloat(Context context, final String key, final float defaultValue) {
        if(context == null){
            return 0;
        }
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getFloat(key, defaultValue);
    }

    public static void setSettingLong(Context context, final String key, final long value) {
        if(context == null){
            return;
        }
        try{
            final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            settings.edit().putLong(key, value).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static long getPrefLong(Context context, final String key, final long defaultValue) {
        if(context == null){
            return 0;
        }
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getLong(key, defaultValue);
    }

    public static void clearPreference(Context context, final SharedPreferences p) {
        try{
            final SharedPreferences.Editor editor = p.edit();
            editor.clear();
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
