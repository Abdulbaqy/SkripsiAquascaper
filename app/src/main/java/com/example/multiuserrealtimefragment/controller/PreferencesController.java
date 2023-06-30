package com.example.multiuserrealtimefragment.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesController {

    private static final String DATA_LOGIN = "status_login",
            DATA_AS = "as",
            DATA_NAME = "name";

    private static SharedPreferences getSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setDataLogin(Context context, String data, String name) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(DATA_AS, data);
        editor.putBoolean(DATA_LOGIN, true);
        editor.putString(DATA_NAME, name);
        editor.apply();
    }

    public static String getDataAs(Context context){
        return getSharedPreferences(context).getString(DATA_AS, "");
    }

    public static boolean getDataLogin(Context context){
        return getSharedPreferences(context).getBoolean(DATA_LOGIN, false);
    }

    public static String getDataName(Context context){
        return getSharedPreferences(context).getString(DATA_NAME, "");
    }

    public static void clearData(Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(DATA_AS);
        editor.remove(DATA_LOGIN);
        editor.remove(DATA_NAME);
        editor.apply();
    }

}
