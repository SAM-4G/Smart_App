package com.sam.kadarairkopi.data;

import org.json.JSONException;
import org.json.JSONObject;

public class DataUser {
    String userEmail;
    String userPassword;
    String userId;
    String userPhone;

    public DataUser(String email, String password, String phone) {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public DataUser(JSONObject object) {
        try {
            String idUser = object.getString("id");
            String emailUser = object.getString("user");
            String passwordUser = object.getString("pass");
//            String phoneUser = object.getString("phone");

            this.userId = idUser;
            this.userEmail = emailUser;
            this.userPassword = passwordUser;
//            this.userPhone = phoneUser;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
