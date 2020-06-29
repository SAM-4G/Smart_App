package com.sam.kadarairkopi.utilityAttribute;

import com.sam.kadarairkopi.BuildConfig;

public class UrlClass {

    public static final String API_KEY = "&api_key=50bfbf83-76db-4cc8-9cc9-eaeb6d5a99b4";

    public static final String Url_Login = BuildConfig.BASE_URL + "login.php?" + API_KEY;
    public static final String Url_DataKopi = BuildConfig.BASE_URL + "getData.php?" + API_KEY;
    public static final String Url_Register = BuildConfig.BASE_URL + "register.php?";
}
