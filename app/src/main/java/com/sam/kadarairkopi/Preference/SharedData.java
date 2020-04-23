package com.sam.kadarairkopi.Preference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.sam.kadarairkopi.Login;
import com.sam.kadarairkopi.MainActivity;

public class SharedData {

    public static final String SHARED_PREF_NAME = "sharedData";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_PHONE = "phone";
    public static final String USER_ID = "id";
    public static final String USER_LOGIN = "statusLogin";

    public static SharedData mInstance;
    public static Context mCtx;

    public SharedData(Context context) {
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

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_EMAIL, null) != null;
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
}
