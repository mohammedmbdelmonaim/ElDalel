package com.zeidex.eldalel.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtils {

    public static boolean saveLocaleKey(Context context, String lang) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_LOCALE, lang);
        editor.apply();
        return true;
    }

    public static String getLocaleKey(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_LOCALE, "ar");
    }


    public static boolean saveUserLogin(Context context, boolean login) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constants.KEY_USERLOGIN, login);
        editor.apply();
        return true;
    }

    public static boolean getUserLogin(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(Constants.KEY_USERLOGIN, false);
    }

    public static boolean saveCompanyLogin(Context context, boolean login) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constants.KEY_COMPANYLOGIN, login);
        editor.apply();
        return true;
    }

    public static boolean getCompanyLogin(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(Constants.KEY_COMPANYLOGIN, false);
    }



    public static boolean saveUriImage(Context context, String photoUri) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.URI_IMAGR, photoUri);
        editor.apply();
        return true;
    }

    public static String getUriImage(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.URI_IMAGR, "");
    }
    public static boolean saveUserToken(Context context, String token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.TOKEN, token);
        editor.apply();
        return true;
    }

    public static String getUserToken(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.TOKEN, "");
    }

    public static boolean saveCompanyToken(Context context, String token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.COMPANYTOKEN, token);
        editor.apply();
        return true;
    }

    public static String getCompanyToken(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.COMPANYTOKEN, "");
    }

    public static boolean saveDeviceToken(Context context, String token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.DEVICE_TOKEN, token);
        editor.apply();
        return true;
    }

    public static String getDeviceToken(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.DEVICE_TOKEN, "");
    }

    public static boolean saveUserName(Context context, String user_name) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.USERNAME, user_name);
        editor.apply();
        return true;
    }

    public static String getUserName(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.USERNAME, "");
    }

    public static boolean saveEmail(Context context, String user_email) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.USEREMAIL, user_email);
        editor.apply();
        return true;
    }

    public static String getEmail(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.USEREMAIL, "");
    }


    public static boolean savePassword(Context context, String user_email) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.USERPASSWORD, user_email);
        editor.apply();
        return true;
    }

    public static String getPassword(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.USERPASSWORD, "");
    }

    public static boolean saveUserPhone(Context context, String user_name) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.USERPHONE, user_name);
        editor.apply();
        return true;
    }

    public static String getUserPhone(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.USERPHONE, "");
    }

    public static boolean saveUserImage(Context context, String user_name) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.USERIMAGE, user_name);
        editor.apply();
        return true;
    }

    public static String getUserImage(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.USERIMAGE, "");
    }

    public static boolean saveCountOfItemsBasket(Context context, int count) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.CountOfItemsBasket, count);
        editor.apply();
        return true;
    }

    public static int getCountOfItemsBasket(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(Constants.CountOfItemsBasket, 0);
    }

    public static boolean saveUserPassword(Context context, String user_password) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_USERPASSWORD, user_password);
        editor.apply();
        return true;
    }

    public static String getUserPassword(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Constants.KEY_USERPASSWORD, "");
    }

}
