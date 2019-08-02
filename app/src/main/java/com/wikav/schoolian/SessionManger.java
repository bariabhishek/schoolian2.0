package com.wikav.schoolian;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManger {
    public static SharedPreferences sharedPref;
    SharedPreferences sharedPreferences,login;
    public static SharedPreferences.Editor editorForResponse;
    public SharedPreferences.Editor loginEditor;
    public SharedPreferences.Editor updateEditor;
    public Context context;
    int Private = 0;

    private static final String PRIF = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String PROFILE_PIC = "PROFILE_PIC";
    public static final String MOBILE = "MOBILE";
    public static final String VERIFIED = "VERIFIED";
    public static final String GENDER = "GENDER";
    public static final String LOCATION = "LOCATION";
    public static final String QUALI = "QUALI";
    public static final String BIO = "BIO";
    public static final String DOB = "DOB";
    public static final String SID = "SID";
    public static final String SCL_COD = "SCL_COD";
    public static final String PHOTO = "PHOTO";
    public static final String SCL_ID = "SCL_ID";
    public static final String CLAS = "CLAS";
    public static final String LASTNAME = "LASTNAME";
    public static final String SCLNAME = "SCLNAME";
    public static final String Scl_Photo = "sclpic";
    public static final String PASS = "PASS";
    public static final String HOMEPAGE_RECORD = "homepagerecords";

    private static final String PREFERENCE_NAME = "APP_PREFERENCE";
    public static final String STAR = "STAR";
    public static final String STARKey = "STARKEY";

    public static final String HOME_FEED_KEY = "home_feed";
    public static final String COMMENT_KEY = "comments";
    public static final String POST = "post";
    public static final String POSTKEY = "postkey";



    public SessionManger(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PRIF, Private);
        login = context.getSharedPreferences(PRIF, Private);
        loginEditor = login.edit();
        updateEditor = sharedPreferences.edit();
    }

    public static void putString(Context context, String key, String value) {
        sharedPref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editorForResponse = sharedPref.edit();
        editorForResponse.putString(key, value);
        editorForResponse.commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }

    public void createSession(String name, String email, String photo, String mobile, String location, String dob, String bio, String sid,String pass,String scl_pic,String scl_id,String classs,String sclname) {
        loginEditor.putBoolean(LOGIN, true);
        updateEditor.putString(NAME, name);
        updateEditor.putString(EMAIL, email);
        updateEditor.putString(PROFILE_PIC, photo);
        updateEditor.putString(DOB, dob);
        updateEditor.putString(BIO, bio);
        updateEditor.putString(MOBILE, mobile);
        updateEditor.putString(LOCATION, location);
        updateEditor.putString(SID, sid);
        updateEditor.putString(PASS, pass);
        updateEditor.putString(Scl_Photo, scl_pic);
        updateEditor.putString(SCL_ID, scl_id);
        updateEditor.putString(CLAS, classs);
        updateEditor.putString(SCLNAME, sclname);
        loginEditor.apply();
        updateEditor.apply();
    }

    public void updateSession(String name, String email,
                              String photo,
                              String mobile,
                              String location, String dob,
                              String bio,
                              String sid,String pass,String scl_pic,String scl_id,String classs,String sclname) {
        updateEditor.putString(NAME, name);
        updateEditor.putString(EMAIL, email);
        updateEditor.putString(PROFILE_PIC, photo);
        updateEditor.putString(MOBILE, mobile);
        updateEditor.putString(LOCATION, location);
        updateEditor.putString(DOB, dob);
        updateEditor.putString(BIO, bio);
        updateEditor.putString(SID, sid);
        updateEditor.putString(PASS, pass);
        updateEditor.putString(Scl_Photo, scl_pic);
        updateEditor.putString(SCL_ID, scl_id);
        updateEditor.putString(CLAS, classs);
        updateEditor.putString(SCLNAME, sclname);
        updateEditor.apply();
    }
        public void clerlast()
        {
            updateEditor.clear();
            updateEditor.commit();
        }


    public boolean isLoging() {

        return login.getBoolean(LOGIN, false);

    }

    public void checkLogin() {
        if (!isLoging()) {

            Intent intent = new Intent(context, Login.class);
            context.startActivity(intent);
            ((Home) context).finish();
        }

    }




    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user = new HashMap<>();
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(PROFILE_PIC, sharedPreferences.getString(PROFILE_PIC, null));
        user.put(MOBILE, sharedPreferences.getString(MOBILE, null));
        user.put(VERIFIED, sharedPreferences.getString(VERIFIED, null));
        user.put(GENDER, sharedPreferences.getString(GENDER, null));
        user.put(LOCATION, sharedPreferences.getString(LOCATION, null));
        user.put(BIO, sharedPreferences.getString(BIO, null));
        user.put(QUALI, sharedPreferences.getString(QUALI, null));
        user.put(SCL_ID, sharedPreferences.getString(SCL_ID, null));
        user.put(SCLNAME, sharedPreferences.getString(SCLNAME, null));
        user.put(SID, sharedPreferences.getString(SID, null));
        user.put(CLAS, sharedPreferences.getString(CLAS, null));
        return user;
    }

    public void logOut() {
        loginEditor.clear();
        updateEditor.clear();
        loginEditor.commit();
        updateEditor.commit();
        Intent intent = new Intent(context, UserMobileNumber.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }



}
