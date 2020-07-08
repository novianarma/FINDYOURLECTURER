package com.example.findyourlecturer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class session {

    /** Pendeklarasian key-data berupa String, untuk sebagai wadah penyimpanan data.
     * Jadi setiap data mempunyai key yang berbeda satu sama lain */
    static final String KEY_USER_TEREGISTER ="user", KEY_PASS_TEREGISTER ="pass";
    static final String KEY_USERNAME_SEDANG_LOGIN = "Username_logged_in";

    static final String KEY_STATUS_SEDANG_LOGIN = "Status_logged_in";
    static final String KEY_STATUS_SEDANG_LOGIN_USER = "Status_logged_in2";



    /** Pendlakarasian Shared Preferences yang berdasarkan paramater context */
    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /** Deklarasi Edit Preferences dan mengubah data
     *  yang memiliki key isi KEY_USER_TEREGISTER dengan parameter username */
    public static void setRegisteredUser(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USER_TEREGISTER, username);
        editor.apply();
    }
    /** Mengembalikan nilai dari key KEY_USER_TEREGISTER berupa String */
    public static String getRegisteredUser(Context context){
        return getSharedPreference(context).getString(KEY_USER_TEREGISTER,"");
    }

    /** Deklarasi Edit Preferences dan mengubah data
     *  yang memiliki key KEY_PASS_TEREGISTER dengan parameter password */
    public static void setRegisteredPass(Context context, String password){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_PASS_TEREGISTER, password);
        editor.apply();
    }
    /** Mengembalikan nilai dari key KEY_PASS_TEREGISTER berupa String */
    public static String getRegisteredPass(Context context){
        return getSharedPreference(context).getString(KEY_PASS_TEREGISTER,"");
    }

    /** Deklarasi Edit Preferences dan mengubah data
     *  yang memiliki key KEY_USERNAME_SEDANG_LOGIN dengan parameter username */
    public static void setLoggedInUser(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USERNAME_SEDANG_LOGIN, username);
        editor.apply();
    }
    /** Mengembalikan nilai dari key KEY_USERNAME_SEDANG_LOGIN berupa String */
    public static String getLoggedInUser(Context context){
        return getSharedPreference(context).getString(KEY_USERNAME_SEDANG_LOGIN,"");
    }

    //ADMIN
    /** Deklarasi Edit Preferences dan mengubah data
     *  yang memiliki key KEY_STATUS_SEDANG_LOGIN dengan parameter status */
    public static void setLoggedInStatusAdmin(Context context, boolean status){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(KEY_STATUS_SEDANG_LOGIN,status);
        editor.apply();
    }
    /** Mengembalikan nilai dari key KEY_STATUS_SEDANG_LOGIN berupa boolean */
    public static boolean getLoggedInStatusAdmin(Context context){
        return getSharedPreference(context).getBoolean(KEY_STATUS_SEDANG_LOGIN,false);
    }

    //USER
    /** Deklarasi Edit Preferences dan mengubah data
     *  yang memiliki key KEY_STATUS_SEDANG_LOGIN dengan parameter status */
    public static void setLoggedInStatusUser(Context context, boolean status){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(KEY_STATUS_SEDANG_LOGIN_USER,status);
        editor.apply();
    }
    /** Mengembalikan nilai dari key KEY_STATUS_SEDANG_LOGIN berupa boolean */
    public static boolean getLoggedInStatusUser(Context context){
        return getSharedPreference(context).getBoolean(KEY_STATUS_SEDANG_LOGIN_USER,false);
    }


    /** Deklarasi Edit Preferences dan menghapus data, sehingga menjadikannya bernilai default
     *  khusus data yang memiliki key KEY_USERNAME_SEDANG_LOGIN dan KEY_STATUS_SEDANG_LOGIN */
    public static void clearLoggedInUser (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(KEY_USERNAME_SEDANG_LOGIN);
        editor.remove(KEY_STATUS_SEDANG_LOGIN);
        editor.apply();
    }


    public static void setService(Context context, int a){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt("tipeuser",a);
        editor.apply();
    }
    public static int getService(Context context, int a){
        return getSharedPreference(context).getInt("tipeuser",a);
    }

    public static void setstatususer(Context context, String tipeuser){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USER_TEREGISTER, tipeuser);
        editor.apply();
    }
    /** Mengembalikan nilai dari key KEY_USER_TEREGISTER berupa String */
    public static String getstatususer(Context context){
        return getSharedPreference(context).getString(KEY_USER_TEREGISTER,"");
    }

}
