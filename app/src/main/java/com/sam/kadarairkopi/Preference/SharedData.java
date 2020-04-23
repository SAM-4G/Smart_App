package com.sam.kadarairkopi.Preference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.sam.kadarairkopi.Login;
import com.sam.kadarairkopi.MainActivity;

public class SharedData {

    private static final String SHARED_PREF_NAME = "sharedData";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";
    private static final String USER_PHONE = "phone";
    private static final String USER_ID = "id";
    public static final String USER_LOGIN_STATUS = "activeLogin";

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

    public void storeUserId(String names) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, names);
        editor.apply();
    }

    public void storeUserEmail(String names) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_EMAIL, names);
        editor.apply();
    }

    public void storeUserPassword(String names) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_PASSWORD, names);
        editor.apply();
    }

    public void storeUserPhone(String names) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_PHONE, names);
        editor.apply();
    }

    public void saveLoggedIn(String keyLog, Boolean value) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(keyLog, value);
        editor.apply();
    }

    public boolean getSaveLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(USER_LOGIN_STATUS, false);
    }

    public String LoggedInUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_EMAIL, null);
    }

    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, Login.class));
    }

    public void loginUser() {
        mCtx.startActivity(new Intent(mCtx, MainActivity.class));
    }
}
