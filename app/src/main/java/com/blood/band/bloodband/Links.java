package com.blood.band.bloodband;

/**
 * Created by Pranto on 8/17/2017.
 */

public class Links {
    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_USER = "user";
    public static final String KEY_PASSWORD = "pass";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_Failed = "failed";
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";
    public static final String SHARED_DASHBOARD = "dashboard";
    public static final String SHARED_URL_DONOR = "url_donor";
    public static final String SHARED_REF_TIME = "reftime";

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "user";
    public static final String TIME_SHARED_PREF = "time";
    public static final String DASHBOARD_SHARED_PREF = "dash";
    public static final String URL_DONOR_SHARED_PREF = "donor";
    //public static final String NAME_SHARED_PREF = "name";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
    public static final String DASH_SHARED_PREF = "dboard";
    public static final String URL_SHARED_PREF = "url";
    public static final String CURTIME_SHARED_PREF = "curtime";
}
