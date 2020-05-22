package com.sam.kadarairkopi.preference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.sam.kadarairkopi.activity.Login;

public class SharedData {

    private static final String SHARED_PREF_NAME = "sharedData";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";
    private static final String USER_PHONE = "phone";
    private static final String USER_ID = "id";
    public static final String USER_LOGIN_STATUS = "activeLogin";
    private static final String WATER_LEVEL_VALUE = "waterLevel";
    public static final String WEIGHT_VALUE = "weight";

    private static SharedData mInstance;
    private static Context mCtx;

    private SharedData(Context context) {
        mCtx = context;
    }

    public static synchronized SharedData getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedData(context);
        }
        return mInstance;
    }

//    Login & Logout Preferences
    public void storeUserEmail(String names) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_EMAIL);
        editor.putString(USER_EMAIL, names);
        editor.apply();
    }
    public void storeUserPassword(String names) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_PASSWORD, names);
        editor.apply();
    }
    public void saveLoggedIn(String keyLog, Boolean value) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(keyLog, value);
        editor.apply();
    }
    public String getUserEmail() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_EMAIL, null);
    }
    public boolean getSaveLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(USER_LOGIN_STATUS, false);
    }
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_PASSWORD);
        editor.remove(USER_LOGIN_STATUS);
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, Login.class));
    }

    //    Data Preferences
    public void storeWaterLevel(String wLevel) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WATER_LEVEL_VALUE, wLevel);
        editor.apply();
    }
    public void storeWeight(String weight) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WEIGHT_VALUE, weight);
        editor.apply();
    }
    public String getWaterLevelValue() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(WATER_LEVEL_VALUE, null);
    }
    public String getWeightValue() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(WEIGHT_VALUE, null);
    }

//    Optional Preferences
    public void storeUserId(String names) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, names);
        editor.apply();
    }
    public void storeUserPhone(String names) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_PHONE, names);
        editor.apply();
    }

}
