package startup.abhishek.spleshscreen;

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

    private static final String PREFERENCE_NAME = "APP_PREFERENCE";


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

    public void createSession(String name, String email, String photo, String verified, String mobile, String gender,String location) {
        loginEditor.putBoolean(LOGIN, true);
        updateEditor.putString(NAME, name);
        updateEditor.putString(EMAIL, email);
        updateEditor.putString(GENDER, gender);
        updateEditor.putString(PROFILE_PIC, photo);
        updateEditor.putString(VERIFIED, verified);
        updateEditor.putString(MOBILE, mobile);
        updateEditor.putString(LOCATION, location);
        loginEditor.apply();
        updateEditor.apply();
    }

    public void updateSession(String name, String email, String photo, String mobile,String location) {
        updateEditor.putString(NAME, name);
        updateEditor.putString(EMAIL, email);
        updateEditor.putString(PROFILE_PIC, photo);
        updateEditor.putString(MOBILE, mobile);
        updateEditor.putString(LOCATION, location);
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
        return user;
    }

    public void logOut() {
        loginEditor.clear();
        updateEditor.clear();
        loginEditor.commit();
        updateEditor.commit();
        Intent intent = new Intent(context, Login.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }



}
